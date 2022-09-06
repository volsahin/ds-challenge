package com.tinyfalcon.dschallenge.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tinyfalcon.dschallenge.feature.home.data.PageLoadingState
import com.tinyfalcon.dschallenge.feature.home.data.PageState
import com.tinyfalcon.dschallenge.feature.home.domain.GetSessions
import com.tinyfalcon.dschallenge.feature.home.domain.SearchSessions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
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
        private const val DEBOUNCE_MILLIS = 600L
    }

    private val _loadingState = MutableStateFlow(PageLoadingState.LOADING)
    val loadingState: StateFlow<PageLoadingState> = _loadingState

    private var pageState = PageState.LIST

    val searchText = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchTextToQuery = searchText
        .debounce(DEBOUNCE_MILLIS)
        .distinctUntilChanged()
        .mapLatest {
            pageState = if (it.isEmpty()) PageState.LIST else PageState.SEARCH
            mediator.invalidate()
        }

    private lateinit var mediator: SessionMediator

    private val pagingSourceFactory = {
        mediator = SessionMediator(pageState, getSessions, searchSessions) {
            _loadingState.value = it
        }
        mediator
    }

    val sessionItemsFlow =
        Pager(PagingConfig(PAGE_SIZE), pagingSourceFactory = pagingSourceFactory).flow.cachedIn(
            viewModelScope
        )
}
