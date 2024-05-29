package com.example.stockmarkteapp.presentation.company_info

import com.example.stockmarkteapp.domain.model.CompanyInfo
import com.example.stockmarkteapp.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfo: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
