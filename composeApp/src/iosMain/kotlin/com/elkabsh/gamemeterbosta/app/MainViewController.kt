package com.elkabsh.gamemeterbosta.app

import androidx.compose.ui.window.ComposeUIViewController
import com.elkabsh.gamemeterbosta.app.di.initKoin
import platform.Foundation.NSBundle
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val apiKey = NSBundle.mainBundle.infoDictionary?.get("API_KEY") as? String ?: ""
    initKoin(apiKey)
    return ComposeUIViewController { AppRoot() }
}
