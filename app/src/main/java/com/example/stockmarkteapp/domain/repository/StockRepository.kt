package com.example.stockmarkteapp.domain.repository

import com.example.stockmarkteapp.domain.model.CompanyInfo
import com.example.stockmarkteapp.domain.model.CompanyList
import com.example.stockmarkteapp.domain.model.IntradayInfo
import com.example.stockmarkteapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>>

    suspend fun getIntradayInfo(
        symbol: String,
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}