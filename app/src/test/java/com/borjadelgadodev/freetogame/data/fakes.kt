package com.borjadelgadodev.freetogame.data

import com.borjadelgadodev.data.GamesRepository
import com.borjadelgadodev.data.datasource.GamesLocalDataSource
import com.borjadelgadodev.data.datasource.GamesRemoteDataSource
import com.borjadelgadodev.domain.Game
import com.borjadelgadodev.freetogame.sampleGames
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

fun buildGamesRepositoryWith (
    localData: List<Game> = emptyList(),
    remoteData: List<Game> = emptyList()
) : GamesRepository {
    val localDataSource = FakeLocalDataSource().apply { inMemoryGames.value = localData }
    val remoteDataSource = FakeRemoteDataSource().apply { games = remoteData }
    
    return GamesRepository(localDataSource, remoteDataSource)
}


class FakeLocalDataSource: GamesLocalDataSource {
    val inMemoryGames = MutableStateFlow<List<Game>>(emptyList())

    override val games: Flow<List<Game>> = inMemoryGames

    override fun getGameById(gameId: Int): Flow<Game?> =
        inMemoryGames.map { games -> games.find { it.id == gameId } }

    override suspend fun saveGames(games: List<Game>) {
        inMemoryGames.value = games
    }
}

class FakeRemoteDataSource: GamesRemoteDataSource {
    var games = sampleGames(1, 2, 3)
    override suspend fun getGames(): List<Game> = games
    override suspend fun getGameById(gameId: Int): Game = games.first { it.id == gameId }
}
