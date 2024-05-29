package com.example.stockmarkteapp.data.mapper

import com.example.stockmarkteapp.data.local.CompanyListEntity
import com.example.stockmarkteapp.data.remote.dto.CompanyInfoDto
import com.example.stockmarkteapp.domain.model.CompanyInfo
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

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        name = name ?: "",
        description = description ?: "",
        symbol = symbol ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}