package com.borjadelgadodev.data

import com.borjadelgadodev.data.datasource.GamesLocalDataSource
import com.borjadelgadodev.data.datasource.GamesRemoteDataSource
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.whenever


class GamesRepositoryTest {

    @Mock
    lateinit var localDataSource: GamesLocalDataSource

    @Mock
    lateinit var remoteDataSource: GamesRemoteDataSource

    private lateinit var gamesRepository: GamesRepository

    @Before
    fun setUp() {
        gamesRepository = GamesRepository(localDataSource, remoteDataSource)
    }


    @Test
    fun `Popular games are taken form local data source if available`() {
        val localGames = sampleGames(1,2)
        whenever(localDataSource.games).thenReturn(flowOf(localGames))
    }
}