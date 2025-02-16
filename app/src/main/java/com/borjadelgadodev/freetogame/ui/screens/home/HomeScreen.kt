package com.borjadelgadodev.freetogame.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.borjadelgadodev.domain.Game
import com.borjadelgadodev.freetogame.R
import com.borjadelgadodev.freetogame.Result
import com.borjadelgadodev.freetogame.ui.components.AcScaffold
import com.borjadelgadodev.freetogame.ui.theme.FreeToGameTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onClick: (Game) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    viewModel.onUiReady()
    val state by viewModel.state.collectAsState()
    HomeScreen(state = state, onClick = onClick)
}

@Composable
fun Screen(content: @Composable () -> Unit) {
    FreeToGameTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: Result<List<Game>>,
    onClick: (Game) -> Unit
) {

    val homeState = rememberHomeState()

    Screen {
        AcScaffold(
            state = state,
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                style = MaterialTheme.typography.titleLarge,
                                text = stringResource(R.string.app_title)
                            )
                        }
                    },
                    scrollBehavior = homeState.scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(homeState.scrollBehavior.nestedScrollConnection)
        ) { padding, games ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                contentPadding = padding
            ) {
                items(games) { game ->
                    GameItem(game = game, onClick = { onClick(game) })
                }
            }
        }
    }
}

@Composable
fun GameItem(game: Game, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box {
            AsyncImage(
                model = game.thumbnail,
                contentDescription = game.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )
            if (game.isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(R.string.favorite),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .size(32.dp)
                )
            }
        }
        Text(
            text = game.title,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
