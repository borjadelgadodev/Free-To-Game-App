package com.borjadelgadodev.freetogame.ui.screens.home

import app.cash.turbine.test
import com.borjadelgadodev.domain.Game
import com.borjadelgadodev.freetogame.Result
import com.borjadelgadodev.freetogame.data.buildGamesRepositoryWith
import com.borjadelgadodev.freetogame.sampleGames
import com.borjadelgadodev.freetogame.testrules.CoroutinesTestRule
import com.borjadelgadodev.usecases.FetchGamesUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HomeIntegrationTests {

    @get:Rule
    val composeTestRule = CoroutinesTestRule()

    @Test
    fun `Data is loaded from server when local data source is empty`() = runTest {
        val remoteData = sampleGames(1, 2, 3)

        val viewModel = HomeViewModel(
            fetchGamesUseCase = FetchGamesUseCase(buildGamesRepositoryWith(remoteData = remoteData))
        )
        viewModel.onUiReady()

        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(emptyList<Game>()), awaitItem())
            assertEquals(Result.Success(remoteData), awaitItem())
        }
    }

    @Test
    fun `Data is loaded from local source when available`() = runTest {
        val localData = sampleGames(1, 2, 3)
        val viewModel = HomeViewModel(
            fetchGamesUseCase = FetchGamesUseCase(buildGamesRepositoryWith(localData = localData))
        )
        viewModel.onUiReady()

        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(localData), awaitItem())
        }
    }
}