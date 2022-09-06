package com.tinyfalcon.dschallenge.feature.home.domain

import com.tinyfalcon.dschallenge.feature.home.data.MusicService
import com.tinyfalcon.dschallenge.feature.home.data.Session
import com.tinyfalcon.dschallenge.feature.home.data.SessionData
import com.tinyfalcon.dschallenge.feature.home.data.SessionResponse
import com.tinyfalcon.dschallenge.feature.home.domain.mapper.SearchSessionsMapper
import com.tinyfalcon.dschallenge.util.TestDispatcherProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchSessionsTest {

    @MockK
    lateinit var service: MusicService

    @MockK
    lateinit var mapper: SearchSessionsMapper

    private val dispatcherProvider = TestDispatcherProvider()

    @InjectMockKs
    lateinit var sut: SearchSessions

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `testSessionMapperTrigger`() {
        runTest(dispatcherProvider.testDispatcher) {
            // Given
            val apiId = "123456"

            val sessionData =
                SessionData(listOf(Session(name = "test_session", 2, listOf("heavy metal"), null)))
            val response = SessionResponse(sessionData)

            coEvery { service.searchSessions(apiId) } answers { response }
            coEvery { mapper.map(response) } answers { mockk() }

            sut.execute(SearchSessions.Params(apiId))

            coVerify { service.searchSessions(apiId) }
            coVerify { mapper.map(response) }
        }
    }
}
