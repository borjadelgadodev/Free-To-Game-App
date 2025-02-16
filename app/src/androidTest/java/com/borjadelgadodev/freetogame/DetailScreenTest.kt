package com.borjadelgadodev.freetogame

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.borjadelgadodev.freetogame.ui.components.LOADING_INDICATOR_TAG
import com.borjadelgadodev.freetogame.ui.screens.detail.DetailScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenLoadingState_showProgress(): Unit = with(composeTestRule) {
        setContent {
            DetailScreen(
                state = Result.Loading,
                onBackClick = {},
                onFavoriteClick = {}
            )
        }
        onNodeWithTag(LOADING_INDICATOR_TAG).assertIsDisplayed()
    }

    @Test
    fun whenErrorState_showError(): Unit = with(composeTestRule) {
        setContent {
            DetailScreen(
                state = Result.Error(RuntimeException("Error")),
                onBackClick = {},
                onFavoriteClick = {}
            )
        }
        onNodeWithText("An error has occurred").assertExists()
    }

    @Test
    fun whenSuccessState_gameIsShown(): Unit = with(composeTestRule) {
        setContent {
            DetailScreen(
                state = Result.Success(sampleGame(2)),
                onBackClick = {},
                onFavoriteClick = {}
            )
        }

        onNodeWithText("Game 2").assertExists()
    }

    @Test
    fun whenGameClicked_listenerForClick(): Unit = with(composeTestRule) {
        var clickedGame = false
        setContent {
            DetailScreen(
                state = Result.Success(sampleGame(1)),
                onBackClick = { clickedGame = true },
                onFavoriteClick = {}
            )
        }

        onNodeWithContentDescription("Mark as favorite").performClick()
        assertTrue(clickedGame)
    }

    @Test
    fun whenBackClicked_listenerForClick(): Unit = with(composeTestRule) {
        var clickedGame = false
        setContent {
            DetailScreen(
                state = Result.Success(sampleGame(1)),
                onBackClick = { clickedGame = true },
                onFavoriteClick = {}
            )
        }

        onNodeWithContentDescription("Back").performClick()
        assertTrue(clickedGame)
    }
}
