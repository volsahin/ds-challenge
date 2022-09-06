package com.tinyfalcon.dschallenge.feature.home.domain

import com.tinyfalcon.dschallenge.base.CoroutineDispatcherProvider
import com.tinyfalcon.dschallenge.base.UseCase
import com.tinyfalcon.dschallenge.feature.home.MapperModule.SearchMapper
import com.tinyfalcon.dschallenge.feature.home.data.MusicService
import com.tinyfalcon.dschallenge.feature.home.domain.mapper.SessionMapper
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchSessions @Inject constructor(
    val dispatcherProvider: CoroutineDispatcherProvider,
    private val service: MusicService,
    @SearchMapper private val sessionMapper: SessionMapper
    // private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UseCase<MusicSessionViewEntity?, SearchSessions.Params>() {

    companion object {
        private const val API_ID = "5df79a3a320000f0612e0115"
    }

    override suspend fun execute(params: Params): MusicSessionViewEntity = withContext(dispatcherProvider.io) {
        val sessions = service.searchSessions(params.apiId)
        sessionMapper.map(sessions)
    }

    data class Params(val apiId: String = API_ID)
}
