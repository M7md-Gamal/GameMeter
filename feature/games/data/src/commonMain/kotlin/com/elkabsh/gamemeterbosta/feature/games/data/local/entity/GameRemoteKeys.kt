package com.elkabsh.gamemeterbosta.feature.games.data.local.entity

import kotlin.time.Clock
import androidx.room.Entity

@Entity(tableName = "game_remote_keys", primaryKeys = ["gameId", "category"])
data class GameRemoteKeys(
        val gameId: Int,
        val category: String,
        val prevPage: Int?,
        val nextPage: Int?,
        val lastUpdated: Long = Clock.System.now().toEpochMilliseconds()
)
