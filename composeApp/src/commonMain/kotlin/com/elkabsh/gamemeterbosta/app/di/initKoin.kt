package com.elkabsh.gamemeterbosta.app.di

import com.elkabsh.gamemeterbosta.core.data.di.CoreProperties
import com.elkabsh.gamemeterbosta.core.data.di.coreDataModule
import com.elkabsh.gamemeterbosta.core.data.di.platformCoreModule
import com.elkabsh.gamemeterbosta.feature.games.data.di.gamesDataModule
import com.elkabsh.gamemeterbosta.feature.games.data.di.platformGamesModule
import com.elkabsh.gamemeterbosta.feature.games.di.gamesPresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(apiKey: String, config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        properties(mapOf(CoreProperties.API_KEY to apiKey))
        modules(
            platformCoreModule,
            coreDataModule,
            platformGamesModule,
            gamesDataModule,
            gamesPresentationModule
        )
    }
}
