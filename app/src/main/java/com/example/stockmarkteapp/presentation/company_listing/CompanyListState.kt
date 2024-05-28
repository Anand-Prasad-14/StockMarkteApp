package com.example.stockmarkteapp.presentation.company_listing

import com.example.stockmarkteapp.domain.model.CompanyList

data class CompanyListState(
    val companies: List<CompanyList> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)