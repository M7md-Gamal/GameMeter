package com.elkabsh.gamemeterbosta.feature.games.data.remote.dto.videos_dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosResponseDto(
    @SerialName("count")
    val count: Int,
    @SerialName("results")
    val results: List<Result>
)