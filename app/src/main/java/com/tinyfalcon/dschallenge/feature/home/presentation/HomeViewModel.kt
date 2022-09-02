package com.tinyfalcon.dschallenge.feature.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tinyfalcon.dschallenge.feature.home.domain.GetSessions
import com.tinyfalcon.dschallenge.feature.home.domain.SearchSessions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalPagingApi::class)
class HomeViewModel
@Inject constructor(
    private val getSessions: GetSessions,
    private val searchSessions: SearchSessions
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    var stateVariable by mutableStateOf(PageState.LIST)

    private val pagingSourceFactory = {
        SessionMediator(stateVariable, getSessions, searchSessions)
    }

    val notificationsFlow =
        Pager(PagingConfig(PAGE_SIZE), pagingSourceFactory = pagingSourceFactory).flow.cachedIn(viewModelScope)


    fun searchState() {
        stateVariable = PageState.SEARCH
    }
}

enum class PageState {
    LIST, SEARCH
}