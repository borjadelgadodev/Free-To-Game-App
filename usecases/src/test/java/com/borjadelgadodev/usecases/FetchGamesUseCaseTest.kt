package com.borjadelgadodev.usecases

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FetchGamesUseCaseTest {
    @Test
    fun `Invoke calls repository`() = runBlocking {

        // Given
        val gameFlow = flowOf(sampleGames(1, 2, 3))

        // When
        val useCase = FetchGamesUseCase(mock {
            on { games } doReturn gameFlow
        })

        val result = useCase()

        // Then
        assertEquals(gameFlow, result)
    }
}
