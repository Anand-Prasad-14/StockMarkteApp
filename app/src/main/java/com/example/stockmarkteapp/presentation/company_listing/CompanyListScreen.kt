package com.example.stockmarkteapp.presentation.company_listing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun CompanyListScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery, onValueChange = {
                viewModel.onEvent(CompanyListEvent.OnSearchQueryChange(it))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.onEvent(CompanyListEvent.Refresh)
        }) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.companies.size) { i ->
                    val company = state.companies[i]
                    CompanyItem(company = company,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                //TODO: Navigate to detail screen
                            }
                            .padding(16.dp))
                    if (i < state.companies.size) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}