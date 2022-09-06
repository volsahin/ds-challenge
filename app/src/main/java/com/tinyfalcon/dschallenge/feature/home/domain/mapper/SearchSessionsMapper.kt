package com.tinyfalcon.dschallenge.feature.home.domain.mapper

import com.tinyfalcon.dschallenge.feature.home.data.SessionResponse
import com.tinyfalcon.dschallenge.feature.home.domain.MusicSessionViewEntity
import com.tinyfalcon.dschallenge.feature.home.domain.SessionDataViewEntity
import com.tinyfalcon.dschallenge.feature.home.domain.SessionViewEntity
import com.tinyfalcon.dschallenge.feature.home.domain.TrackViewEntity
import javax.inject.Inject

class SearchSessionsMapper @Inject constructor() : SessionMapper {
    override fun map(sessionResponse: SessionResponse?): MusicSessionViewEntity {
        val sessionsViewEntity = mutableListOf<SessionViewEntity>()
        sessionResponse?.data?.sessions?.shuffled()?.forEach {
            sessionsViewEntity.add(
                SessionViewEntity(
                    it.name,
                    it.listenerCount,
                    it.genres,
                    TrackViewEntity(it.currentTrack?.title, it.currentTrack?.artworkUrl)
                )
            )
        }
        val sessionDataViewEntity = SessionDataViewEntity(sessionsViewEntity)
        return MusicSessionViewEntity(sessionDataViewEntity)
    }
}
