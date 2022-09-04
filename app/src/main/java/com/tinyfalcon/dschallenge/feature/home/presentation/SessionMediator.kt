package com.tinyfalcon.dschallenge.feature.home.presentation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tinyfalcon.dschallenge.feature.home.data.PageLoadingState
import com.tinyfalcon.dschallenge.feature.home.data.PageState
import com.tinyfalcon.dschallenge.feature.home.domain.GetSessions
import com.tinyfalcon.dschallenge.feature.home.domain.MusicSessionViewEntity
import com.tinyfalcon.dschallenge.feature.home.domain.SearchSessions
import com.tinyfalcon.dschallenge.feature.home.domain.SessionViewEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ExperimentalPagingApi
class SessionMediator @Inject constructor(
    private val currentPageState: PageState,
    private val getSessions: GetSessions,
    private val searchSessions: SearchSessions,
    private val onPageLoad: (PageLoadingState) -> (Unit)
): PagingSource<Int, SessionViewEntity>() {

    companion object {
        private const val FIRST_PAGE = 1
        private const val PAGING_LIMIT = 5
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SessionViewEntity> {
        val nextPage = params.key ?: FIRST_PAGE

        onPageLoad(PageLoadingState.LOADING)

        val sessionViewEntity: MusicSessionViewEntity = if (currentPageState == PageState.LIST) {
            getSessions.execute()
        } else {
            searchSessions.execute()
        }

        delay(600)

        onPageLoad(PageLoadingState.READY)

        sessionViewEntity.data?.sessions?.let {
            return LoadResult.Page(
                data = it,
                prevKey = if (nextPage == FIRST_PAGE) null else nextPage - 1,
                nextKey = if (nextPage == PAGING_LIMIT) null else nextPage + 1
            )
        }

        // TODO
        return LoadResult.Error(IllegalArgumentException())
    }

    override fun getRefreshKey(state: PagingState<Int, SessionViewEntity>): Int? = null
}