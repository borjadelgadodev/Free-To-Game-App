package com.borjadelgadodev.freetogame.ui.screens.detail

import app.cash.turbine.test
import com.borjadelgadodev.freetogame.Result
import com.borjadelgadodev.freetogame.sampleGame
import com.borjadelgadodev.freetogame.testrules.CoroutinesTestRule
import com.borjadelgadodev.usecases.FindGameByIdUseCase
import com.borjadelgadodev.usecases.ToggleFavoriteUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var findGameByIdUseCase: FindGameByIdUseCase

    @Mock
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    private lateinit var viewModel: DetailViewModel

    private val game = sampleGame(2)

    @Before
    fun setUp() {
        whenever(findGameByIdUseCase(2)).thenReturn(flowOf(game))
        viewModel = DetailViewModel(2, findGameByIdUseCase, toggleFavoriteUseCase)
    }



    @Test
    fun `UI is updated with the game on start`() = runTest {
        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(game), awaitItem())
        }
    }

    @Test
    fun `Favorite action calls the corresponding use case`() = runTest {
        viewModel.state.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(game), awaitItem())

            viewModel.onFavoriteClick()
            runCurrent()

            verify(toggleFavoriteUseCase).invoke(game)
        }
    }
}