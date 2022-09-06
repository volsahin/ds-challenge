package com.tinyfalcon.dschallenge.feature.home.domain

import com.tinyfalcon.dschallenge.base.CoroutineDispatcherProvider
import com.tinyfalcon.dschallenge.base.UseCase
import com.tinyfalcon.dschallenge.feature.home.MapperModule.GetSessionMapper
import com.tinyfalcon.dschallenge.feature.home.data.MusicService
import com.tinyfalcon.dschallenge.feature.home.domain.mapper.SessionMapper
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSessions @Inject constructor(
    private val dispatcherProvider: CoroutineDispatcherProvider,
    private val service: MusicService,
    @GetSessionMapper private val sessionMapper: SessionMapper
) : UseCase<MusicSessionViewEntity, GetSessions.Params>() {

    companion object {
        private const val API_ID = "5df79b1f320000f4612e011e"
    }

    override suspend fun execute(params: Params): MusicSessionViewEntity = withContext(dispatcherProvider.io) {
        val sessions = service.getSessions(params.apiId)
        sessionMapper.map(sessions)
    }

    data class Params(val apiId: String = API_ID)
}
