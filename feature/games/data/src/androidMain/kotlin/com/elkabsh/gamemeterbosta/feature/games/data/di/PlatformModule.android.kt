package com.elkabsh.gamemeterbosta.feature.games.data.di

import androidx.room.Room
import com.elkabsh.gamemeterbosta.feature.games.data.local.GamesDB
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformGamesModule: Module = module {
    single<GamesDatabaseBuilder> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = GamesDB::class.java,
            name = GAMES_DB_NAME
        )
    }
}
