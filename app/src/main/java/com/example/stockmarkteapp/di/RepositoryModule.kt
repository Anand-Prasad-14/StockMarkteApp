package com.example.stockmarkteapp.di

import com.example.stockmarkteapp.data.CSV.CSVParser
import com.example.stockmarkteapp.data.CSV.CompanyListParser
import com.example.stockmarkteapp.data.repository.StockRepositoryImpl
import com.example.stockmarkteapp.domain.model.CompanyList
import com.example.stockmarkteapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCompanyListParser(
        companyListParser: CompanyListParser
    ): CSVParser<CompanyList>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}