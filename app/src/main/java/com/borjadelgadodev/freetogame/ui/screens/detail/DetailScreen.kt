package com.borjadelgadodev.freetogame.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.borjadelgadodev.domain.Game
import com.borjadelgadodev.freetogame.Result
import com.borjadelgadodev.freetogame.ui.components.AcScaffold
import com.borjadelgadodev.freetogame.ui.components.CustomSnackbar
import com.borjadelgadodev.freetogame.ui.screens.home.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    vm: DetailViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val state by vm.state.collectAsState()

    DetailScreen(
        state = state,
        onBackClick = onBackClick,
        onFavoriteClick = { vm.onFavoriteClick() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: Result<Game?>,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val detailState = rememberDetailState()

    Screen {
        AcScaffold(
            state = state,
            topBar = {
                DetailTopBar(
                    title = (state as? Result.Success)?.data?.title ?: "",
                    scrollBehavior = detailState.scrollBehavior,
                    onBackClick = onBackClick
                )
            },
            floatingActionButton = {
                val isFavorite = (state as? Result.Success)?.data?.isFavorite ?: false
                FloatingActionButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                }
            },
            snackbarHostState = {
                CustomSnackbar(snackbarHostState = detailState.snackbarHostState) {
                    onFavoriteClick()
                }
            },
            modifier = Modifier.nestedScroll(detailState.scrollBehavior.nestedScrollConnection)
        ) { padding, game ->
            if (game != null) {
                GameDetail(
                    game = game,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailTopBar(
    title: String, onBackClick: () -> Unit, scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(title = { Text(text = title) }, navigationIcon = {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
    }, scrollBehavior = scrollBehavior
    )
}

@Composable
private fun GameDetail(game: Game, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = game.thumbnail,
            contentDescription = game.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
        Text(
            text = buildAnnotatedString {
                game.description?.let { Property("Description", it) }
                game.genre?.let { Property("Genre", it) }
                game.platform?.let { Property("Platform", it) }
                game.developer?.let { Property("Developer", it, end = true) }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp),

            )
    }
}

@Composable
private fun AnnotatedString.Builder.Property(key: String, value: String, end: Boolean = false) {
    withStyle(ParagraphStyle(lineHeight = 20.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$key: ")
        }
        append(value)
        if (!end) {
            append("\n")
        }
    }
}
