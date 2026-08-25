package com.elkabsh.gamemeterbosta.feature.games.domain.model

data class GameListItem(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val backgroundImage: String,
    val rating: Double
)