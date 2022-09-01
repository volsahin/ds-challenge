package com.tinyfalcon.dschallenge.feature.home.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tinyfalcon.dschallenge.feature.home.domain.SearchSessions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalPagingApi::class)
class HomeViewModel
@Inject constructor(
    private val sessionMediator: SessionMediator,
    private val savedStateHandle: SavedStateHandle,
    private val searchSessions: SearchSessions,
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
        private const val STATE_KEY = "_state"
    }

    val notificationsFlow =
        Pager(PagingConfig(PAGE_SIZE)) { sessionMediator }.flow.cachedIn(viewModelScope)

    fun searchSessions() {
        val savedState = savedStateHandle.get<PageState>(STATE_KEY)

        viewModelScope.launch(Dispatchers.IO) {
            searchSessions.execute()
        }

    }
}

enum class PageState {
    LIST, SEARCH
}