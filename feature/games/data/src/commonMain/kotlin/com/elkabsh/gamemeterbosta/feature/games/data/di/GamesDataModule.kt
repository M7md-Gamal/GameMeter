package com.elkabsh.gamemeterbosta.feature.games.data.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.elkabsh.gamemeterbosta.feature.games.data.local.GamesDB
import com.elkabsh.gamemeterbosta.feature.games.data.local.dao.GameDao
import com.elkabsh.gamemeterbosta.feature.games.data.local.dao.GameDetailsDao
import com.elkabsh.gamemeterbosta.feature.games.data.remote.KtorRemoteGameDataSource
import com.elkabsh.gamemeterbosta.feature.games.data.remote.RemoteGameDataSource
import com.elkabsh.gamemeterbosta.feature.games.data.repo.GamesRepoImpl
import com.elkabsh.gamemeterbosta.feature.games.domain.repo.GamesRepo
import org.koin.dsl.module

val gamesDataModule = module {
    single<GamesDB> {
        get<GamesDatabaseBuilder>()
            .fallbackToDestructiveMigration(false)
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single<GameDao> { get<GamesDB>().gameDao() }
    single<GameDetailsDao> { get<GamesDB>().gameDetailsDao() }
    single<RemoteGameDataSource> { KtorRemoteGameDataSource(get()) }
    single<GamesRepo> {
        GamesRepoImpl(
            remoteDataSource = get(),
            gameDatabase = get()
        )
    }
}
