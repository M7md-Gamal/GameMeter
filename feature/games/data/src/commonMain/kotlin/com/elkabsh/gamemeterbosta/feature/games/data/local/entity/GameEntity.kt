package com.elkabsh.gamemeterbosta.feature.games.data.local.entity

import kotlin.time.Clock
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
        @PrimaryKey val id: Int,
        val name: String,
        val releaseDate: String,
        val backgroundImage: String,
        val rating: Double,
        val category: List<String>,
        val lastUpdated: Long = Clock.System.now().toEpochMilliseconds()
)
