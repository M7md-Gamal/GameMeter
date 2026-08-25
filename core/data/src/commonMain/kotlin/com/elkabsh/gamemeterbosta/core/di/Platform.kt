package com.elkabsh.gamemeterbosta.core.data.di

import com.elkabsh.gamemeterbosta.core.domain.errors.DataError
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module

object CoreProperties {
    const val API_KEY = "API_KEY"
}

expect val platformCoreModule: Module

internal expect fun debugLog(tag: String, message: String)

expect fun Throwable.asRemoteDataError(): DataError.Remote?
