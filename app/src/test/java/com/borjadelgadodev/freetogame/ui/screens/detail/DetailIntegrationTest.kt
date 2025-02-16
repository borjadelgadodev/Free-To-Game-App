package com.borjadelgadodev.freetogame.ui.screens.detail

import app.cash.turbine.test
import com.borjadelgadodev.freetogame.Result
import com.borjadelgadodev.freetogame.data.buildGamesRepositoryWith
import com.borjadelgadodev.freetogame.sampleGame
import com.borjadelgadodev.freetogame.sampleGames
import com.borjadelgadodev.freetogame.testrules.CoroutinesTestRule
import com.borjadelgadodev.usecases.FindGameByIdUseCase
import com.borjadelgadodev.usecases.ToggleFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTest {

    @get:Rule
    val composeTestRule = CoroutinesTestRule()

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp(){
        val gamesRepository = buildGamesRepositoryWith(
            localData = sampleGames(1, 2, 3),
            remoteData = sampleGames(1, 2, 3)
        )
        viewModel = DetailViewModel(
            2,
            FindGameByIdUseCase(gamesRepository),
            ToggleFavoriteUseCase(gamesRepository)
        )
    }

    @Test
    fun `UI is updated with the game on start`() = runTest {
        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(sampleGame(2)), awaitItem())
        }
    }

    @Test
    fun `Favorite is updated in local data source`() = runTest {
        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(sampleGame(2)), awaitItem())

            viewModel.onFavoriteClick()
            runCurrent()

            assertEquals(Result.Success(sampleGame(2).copy(isFavorite = true)), awaitItem())
        }
    }
}