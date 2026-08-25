package com.elkabsh.gamemeterbosta.feature.games.data.di

import androidx.room.Room
import com.elkabsh.gamemeterbosta.feature.games.data.local.GamesDB
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual val platformGamesModule: Module = module {
    single<GamesDatabaseBuilder> {
        val documentDirectory = NSFileManager.defaultManager.URLsForDirectory(
            NSDocumentDirectory,
            NSUserDomainMask
        ).first() as NSURL
        val dbPath = requireNotNull(
            documentDirectory.URLByAppendingPathComponent(GAMES_DB_NAME)?.path
        )

        Room.databaseBuilder<GamesDB>(name = dbPath)
    }
}
