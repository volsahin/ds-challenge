package com.tinyfalcon.dschallenge.feature.home.domain

import com.tinyfalcon.dschallenge.base.UseCase
import com.tinyfalcon.dschallenge.feature.home.MapperModule.SearchMapper
import com.tinyfalcon.dschallenge.feature.home.data.MusicService
import com.tinyfalcon.dschallenge.feature.home.domain.mapper.SessionMapper
import javax.inject.Inject

class SearchSessions @Inject constructor(
    private val service: MusicService,
    @SearchMapper private val sessionMapper: SessionMapper
): UseCase<MusicSessionViewEntity?>() {

    companion object {
        private const val API_ID = "5df79a3a320000f0612e0115"
    }

    override suspend fun execute(): MusicSessionViewEntity {
        val sessions = service.searchSessions(API_ID)
        return sessionMapper.map(sessions)
    }
}