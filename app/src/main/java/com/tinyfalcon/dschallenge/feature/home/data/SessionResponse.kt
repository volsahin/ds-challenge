package com.tinyfalcon.dschallenge.feature.home.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionResponse(
    @Json(name = "data") val data: SessionData?
)

@JsonClass(generateAdapter = true)
data class SessionData(
    @Json(name = "sessions") val sessions: List<Session>?
)

@JsonClass(generateAdapter = true)
data class Session(
    @Json(name = "name") val name: String?,
    @Json(name = "listener_count") val listenerCount: Int?,
    @Json(name = "genres") val genres: List<String?>?,
    @Json(name = "current_track") val currentTrack: Track?
)

@JsonClass(generateAdapter = true)
data class Track(
    @Json(name = "title") val title: String? = null,
    @Json(name = "artwork_url") val artworkUrl: String? = null
)
