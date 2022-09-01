package com.tinyfalcon.dschallenge.feature.home.data

import retrofit2.http.GET
import retrofit2.http.Path

interface MusicService {
    @GET("{api_id}")
    suspend fun getSessions(@Path("api_id") apiId: String): SessionResponse?

    @GET("{api_id}")
    suspend fun searchSessions(@Path("api_id") apiId: String): SessionResponse?
}