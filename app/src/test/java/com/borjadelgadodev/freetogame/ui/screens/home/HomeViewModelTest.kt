package com.borjadelgadodev.freetogame.ui.screens.home

import app.cash.turbine.test
import com.borjadelgadodev.freetogame.Result
import com.borjadelgadodev.freetogame.sampleGames
import com.borjadelgadodev.freetogame.testrules.CoroutinesTestRule
import com.borjadelgadodev.usecases.FetchGamesUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(fetchGamesUseCase)
    }

    @Mock
    lateinit var fetchGamesUseCase: FetchGamesUseCase

    @Test
    fun `Games are not requested if UI is not ready`() = runTest {
        viewModel.state.first()
        runCurrent()

        verify(fetchGamesUseCase,times(0)).invoke()
    }

    @Test
    fun `Games are requested if UI is ready`() = runTest {
        val games = sampleGames(1, 2, 3)
        whenever(fetchGamesUseCase.invoke()).thenReturn(flowOf(games))

        viewModel.onUiReady()

        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(games), awaitItem())
        }
    }

    @Test
    fun `Error is propagated when request fails`() = runTest {
        val error = RuntimeException("Error")
        whenever(fetchGamesUseCase()).thenThrow(error)

        viewModel.onUiReady()

        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Error(error), awaitItem())
        }
    }
}