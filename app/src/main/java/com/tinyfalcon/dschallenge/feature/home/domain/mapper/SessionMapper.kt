package com.tinyfalcon.dschallenge.feature.home.domain.mapper

import com.tinyfalcon.dschallenge.feature.home.data.SessionResponse
import com.tinyfalcon.dschallenge.feature.home.domain.MusicSessionViewEntity

interface SessionMapper {
    fun map(sessionResponse: SessionResponse?): MusicSessionViewEntity
}
