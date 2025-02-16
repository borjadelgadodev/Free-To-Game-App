package com.borjadelgadodev.usecases

import com.borjadelgadodev.data.GamesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ToggleFavoriteUseCaseTest {
    @Test
    fun `Invoke calls repository`(): Unit = runBlocking {

        // Given
        val game = sampleGame(1)

        // When
        val repository = mock<GamesRepository> ()
        val useCase = ToggleFavoriteUseCase(repository)

        useCase(game)

        // Then
        verify(repository).toggleFavorite(game)
    }
}
