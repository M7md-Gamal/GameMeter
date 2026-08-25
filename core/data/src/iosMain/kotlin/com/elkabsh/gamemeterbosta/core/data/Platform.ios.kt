package com.elkabsh.gamemeterbosta.core.data.di

import com.elkabsh.gamemeterbosta.core.domain.errors.DataError
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.engine.darwin.DarwinHttpRequestException
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformCoreModule: Module = module {
    single<HttpClientEngine> { Darwin.create() }
}

internal actual fun debugLog(tag: String, message: String) {
    println("$tag: $message")
}

actual fun Throwable.asRemoteDataError(): DataError.Remote? {
    return if (this is DarwinHttpRequestException) DataError.Remote.NO_INTERNET else null
}
