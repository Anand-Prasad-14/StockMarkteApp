package com.example.stockmarkteapp.data.repository

import com.example.stockmarkteapp.data.CSV.CSVParser
import com.example.stockmarkteapp.data.local.StockDatabase
import com.example.stockmarkteapp.data.mapper.toCompanyInfo
import com.example.stockmarkteapp.data.mapper.toCompanyList
import com.example.stockmarkteapp.data.mapper.toCompanyListEntity
import com.example.stockmarkteapp.data.remote.StockApi
import com.example.stockmarkteapp.domain.model.CompanyInfo
import com.example.stockmarkteapp.domain.model.CompanyList
import com.example.stockmarkteapp.domain.model.IntradayInfo
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
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListParser: CSVParser<CompanyList>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
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

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val  results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        } catch (e: IOException){
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info")
        } catch (e: HttpException){
            e.printStackTrace()
            Resource.Error(message = "Coudn't load intraday info")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        }catch (e: IOException){
            e.printStackTrace()
            Resource.Error(message = "Couldn't load company info")
        } catch (e: HttpException){
            e.printStackTrace()
            Resource.Error(message = "Coudn't load company info")
        }
    }
}