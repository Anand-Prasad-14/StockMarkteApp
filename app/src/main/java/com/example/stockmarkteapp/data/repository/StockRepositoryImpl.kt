package com.example.stockmarkteapp.data.repository

import com.example.stockmarkteapp.data.CSV.CSVParser
import com.example.stockmarkteapp.data.local.StockDatabase
import com.example.stockmarkteapp.data.mapper.toCompanyList
import com.example.stockmarkteapp.data.mapper.toCompanyListEntity
import com.example.stockmarkteapp.data.remote.StockApi
import com.example.stockmarkteapp.domain.model.CompanyList
import com.example.stockmarkteapp.domain.repository.StockRepository
import com.example.stockmarkteapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListParser: CSVParser<CompanyList>
): StockRepository {
    private val dao = db.dao
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListing.map { it.toCompanyList() }
            ))

            val isDbEmpty = localListing.isEmpty() && query.isBlank()
            val LoadFromCache = !isDbEmpty && !fetchFromRemote

            if (LoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteList = try {
                val response = api.getListings()
                companyListParser.parse(response.byteStream())
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error("Could't load data"))
                null
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Could't load data"))
                null
            }

            remoteList?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListEntity() }
                )
                emit(Resource.Success(
                    data = dao
                        .searchCompanyListing("")
                        .map { it.toCompanyList() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }
}