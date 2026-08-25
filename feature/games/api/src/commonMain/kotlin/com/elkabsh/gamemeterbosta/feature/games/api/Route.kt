package com.elkabsh.gamemeterbosta.feature.games.api

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object GamesList: Route
    @Serializable data class GameDetail(val id:Int): Route
}