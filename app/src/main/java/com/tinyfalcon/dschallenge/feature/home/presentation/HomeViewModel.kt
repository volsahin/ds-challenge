package com.tinyfalcon.dschallenge.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinyfalcon.dschallenge.feature.home.domain.GetSessions
import com.tinyfalcon.dschallenge.feature.home.domain.SearchSessions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getSessions: GetSessions,
    private val searchSessions: SearchSessions
): ViewModel() {

    fun getSessions() {
        viewModelScope.launch(Dispatchers.IO) {
            getSessions.execute()
        }
    }

    fun searchSessions() {
        viewModelScope.launch(Dispatchers.IO) {
            getSessions.execute()
        }
    }
}