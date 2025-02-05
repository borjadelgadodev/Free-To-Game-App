package com.borjadelgadodev.usecases

import com.borjadelgadodev.domain.Game
import com.borjadelgadodev.domain.MinimumSystemRequirements

fun sampleGame(id: Int) = Game(
    id = id,
    title = "Game $id",
    description = "Description $id",
    thumbnail = "thumbnail1.jpg",
    genre = "Action",
    platform = "PC",
    developer = "Developer $id",
    isFavorite = false,
    minimumSystemRequirements = MinimumSystemRequirements(
        os = "Windows 10",
        processor = "Intel Core i5",
        memory = "8GB",
        graphics = "NVIDIA GTX 1060",
        storage = "50GB"
    )
)

fun sampleGames(vararg ids: Int) = ids.map { sampleGame(it) }