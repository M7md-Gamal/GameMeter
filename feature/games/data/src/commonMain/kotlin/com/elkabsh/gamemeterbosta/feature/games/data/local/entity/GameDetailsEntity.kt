package com.elkabsh.gamemeterbosta.feature.games.data.local.entity

import kotlin.time.Clock
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_details")
data class GameDetailsEntity(
    @PrimaryKey
    val id: Int,
    val description: String,
    val screenshotsUrl: String, // Store as JSON string
    val lastUpdated: Long = Clock.System.now().toEpochMilliseconds()
)