package com.tinyfalcon.dschallenge.feature.home.domain

import com.tinyfalcon.dschallenge.base.UseCase
import com.tinyfalcon.dschallenge.feature.home.data.MusicService
import com.tinyfalcon.dschallenge.feature.home.data.SessionResponse
import javax.inject.Inject

class SearchSessions @Inject constructor(
    private val service: MusicService
): UseCase<SessionResponse?>() {

    override suspend fun execute() = service.searchSessions("5df79a3a320000f0612e0115")
}