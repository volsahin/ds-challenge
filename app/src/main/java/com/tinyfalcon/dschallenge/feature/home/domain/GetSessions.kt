package com.tinyfalcon.dschallenge.feature.home.domain

import com.tinyfalcon.dschallenge.base.UseCase
import com.tinyfalcon.dschallenge.feature.home.data.MusicService
import com.tinyfalcon.dschallenge.feature.home.data.SessionResponse
import javax.inject.Inject

class GetSessions @Inject constructor(
    private val service: MusicService
): UseCase<SessionResponse?>() {

    override suspend fun execute() = service.getSessions("5df79b1f320000f4612e011e")
}