package com.elkabsh.gamemeterbosta.app

import android.app.Application
import com.elkabsh.gamemeterbosta.BuildConfig
import com.elkabsh.gamemeterbosta.app.di.initKoin
import org.koin.android.ext.koin.androidContext

class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(BuildConfig.API_KEY) {
            androidContext(this@AppClass)
        }
    }
}
