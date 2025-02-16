package com.borjadelgadodev.freetogame

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.borjadelgadodev.freetogame.ui.components.LOADING_INDICATOR_TAG
import com.borjadelgadodev.freetogame.ui.screens.home.HomeScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgress(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                state = Result.Loading,
                onClick = {}
            )
        }

        onNodeWithTag(LOADING_INDICATOR_TAG).assertIsDisplayed()
    }

    @Test
    fun whenErrorState_showError(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                state = Result.Error(RuntimeException("Error")),
                onClick = {}
            )
        }

        onNodeWithTag("An error has occurred").assertExists()
    }

    @Test
    fun whenSuccessState_showGames(): Unit = with(composeTestRule) {
        setContent {
            HomeScreen(
                state = Result.Success(
                    sampleGames(1, 2, 3)
                ),
                onClick = {}
            )
        }

        onNodeWithText("Game 1").assertExists()
    }

    @Test
    fun whenGameClicked_listenForClick(): Unit = with(composeTestRule) {
        var clickedGame = -1
        val games = sampleGames(1, 2, 3)
        setContent {
            HomeScreen(
                state = Result.Success(games),
                onClick = { clickedGame = it.id }
            )
        }

        onNodeWithText("Title 1").performClick()

        assertEquals(1, clickedGame)
    }
}
