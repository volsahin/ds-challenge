package com.tinyfalcon.dschallenge.feature.home.domain

data class MusicSessionViewEntity(
    val data: SessionDataViewEntity?
)

data class SessionDataViewEntity(
    val sessions: List<SessionViewEntity>?
)

data class SessionViewEntity(
    val name: String?,
    val listenerCount: Int?,
    val genres: List<String?>?,
    val currentTrack: TrackViewEntity?
)

data class TrackViewEntity(
    val title: String? = null,
    val artworkUrl: String? = null
)

