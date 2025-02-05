package com.borjadelgadodev.usecases

import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class FindGameByIdUseCaseTest {
    @Test
    fun `Invoke calls repository`() {

        // Given
        val gameFlow = flowOf(sampleGame(1))

        // When
        val useCase = FindGameByIdUseCase(mock {
            on { getGameById(1) } doReturn gameFlow
        })

        val result = useCase(1)

        // Then
        assertEquals(gameFlow, result)
    }
}
