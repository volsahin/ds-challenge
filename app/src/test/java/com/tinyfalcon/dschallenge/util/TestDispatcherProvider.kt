package com.tinyfalcon.dschallenge.util

import com.tinyfalcon.dschallenge.base.CoroutineDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestDispatcherProvider : CoroutineDispatcherProvider() {

    val testDispatcher = StandardTestDispatcher()

    override val main: CoroutineContext = testDispatcher

    override val io: CoroutineContext = testDispatcher
}
