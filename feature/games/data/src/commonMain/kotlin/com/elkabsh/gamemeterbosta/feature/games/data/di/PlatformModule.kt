package com.elkabsh.gamemeterbosta.feature.games.data.di

import androidx.room.RoomDatabase
import com.elkabsh.gamemeterbosta.feature.games.data.local.GamesDB
import org.koin.core.module.Module

expect val platformGamesModule: Module

internal const val GAMES_DB_NAME = "games_database.db"

typealias GamesDatabaseBuilder = RoomDatabase.Builder<GamesDB>
