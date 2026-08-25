package com.elkabsh.gamemeterbosta.core.data.di


import android.util.Log
import com.elkabsh.gamemeterbosta.core.domain.errors.DataError
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException

actual val platformCoreModule: Module = module {
    single<HttpClientEngine> { OkHttp.create() }
}

internal actual fun debugLog(tag: String, message: String) {
    Log.d(tag, message)
}

actual fun Throwable.asRemoteDataError(): DataError.Remote? {
    return when (this) {
        is ConnectException, is UnknownHostException, is SocketException -> DataError.Remote.NO_INTERNET
        else -> null
    }
}
