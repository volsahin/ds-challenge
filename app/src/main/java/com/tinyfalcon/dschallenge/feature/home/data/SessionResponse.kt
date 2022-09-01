package com.tinyfalcon.dschallenge.feature.home.data

import com.squareup.moshi.Json

data class SessionResponse(
    @Json(name = "data") var data: SessionData?
)

data class SessionData(
    @Json(name = "sessions") var sessions: List<Session?>?
)

data class Session(
    @Json(name = "name") var name: String?,
    @Json(name = "listener_count") var listenerCount: Int?,
    @Json(name = "genres") var genres: List<String?>?,
    @Json(name = "current_track") var currentTrack: Track?
)

data class Track(
    @Json(name = "title") var title: String? = null,
    @Json(name = "artwork_url") var artworkUrl: String? = null
)

