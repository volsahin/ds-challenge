package com.tinyfalcon.dschallenge.feature.home.presentation

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tinyfalcon.dschallenge.feature.home.data.Session
import com.tinyfalcon.dschallenge.feature.home.domain.GetSessions
import com.tinyfalcon.dschallenge.feature.home.domain.SearchSessions
import javax.inject.Inject

@ExperimentalPagingApi
class SessionMediator @Inject constructor(
    private val getSessions: GetSessions,
    private val searchSessions: SearchSessions
): PagingSource<Int, Session>() {

    private var currentPageIndex = 0

    private var currentPageState = PageState.LIST

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Session> {
        val nextPage = params.key ?: 1

        val sessionResponse = getSessions.execute()

        sessionResponse?.data?.sessions?.let {
            return LoadResult.Page(
                data = it,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = ++currentPageIndex
            )
        }

        return LoadResult.Error(IllegalArgumentException())
    }

    override fun getRefreshKey(state: PagingState<Int, Session>): Int? = null
}