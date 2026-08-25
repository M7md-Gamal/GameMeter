package com.elkabsh.gamemeterbosta.core.data.di

import com.elkabsh.gamemeterbosta.core.data.di.CoreProperties
import com.elkabsh.gamemeterbosta.core.data.remote.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory.create(
            engine = get(),
            apiKey = getProperty(CoreProperties.API_KEY)
        )
    }
}
