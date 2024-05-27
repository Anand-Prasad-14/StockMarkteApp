package com.example.stockmarkteapp.data.mapper

import com.example.stockmarkteapp.data.local.CompanyListEntity
import com.example.stockmarkteapp.domain.model.CompanyList

fun CompanyListEntity.toCompanyList(): CompanyList {
    return CompanyList(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyList.toCompanyListEntity(): CompanyListEntity {
    return CompanyListEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}