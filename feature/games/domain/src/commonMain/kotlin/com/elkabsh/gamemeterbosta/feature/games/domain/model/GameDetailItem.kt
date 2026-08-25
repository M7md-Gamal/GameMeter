package com.elkabsh.gamemeterbosta.feature.games.domain.model

data class GameDetailItem(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val backgroundImage: String,
    val rating: Double,
    val description: String,
    val screenshotsUrl: List<String>,
    val category: List<String>

)