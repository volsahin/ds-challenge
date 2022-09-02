package com.tinyfalcon.dschallenge.feature.home.domain

import com.tinyfalcon.dschallenge.base.UseCase
import com.tinyfalcon.dschallenge.feature.home.MapperModule.GetSessionMapper
import com.tinyfalcon.dschallenge.feature.home.data.MusicService
import com.tinyfalcon.dschallenge.feature.home.domain.mapper.SessionMapper
import javax.inject.Inject

class GetSessions @Inject constructor(
    private val service: MusicService,
    @GetSessionMapper private val sessionMapper: SessionMapper
): UseCase<MusicSessionViewEntity>() {

    companion object {
        private const val API_ID = "5df79b1f320000f4612e011e"
    }

    override suspend fun execute(): MusicSessionViewEntity {
        val sessions = service.getSessions(API_ID)
        return sessionMapper.map(sessions)
    }
}