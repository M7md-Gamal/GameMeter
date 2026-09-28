package com.elkabsh.gamemeterbosta.feature.games.data.remote.dto.videos_dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("max")
    val max: String,
    @SerialName("480")
    val x480: String
)