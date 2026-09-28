package com.elkabsh.gamemeterbosta.feature.games.presentation.game_details.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import platform.UIKit.UIApplication

@Composable
actual fun KeepScreenOn(keepScreenOn: Boolean) {
    DisposableEffect(keepScreenOn) {
        UIApplication.sharedApplication.idleTimerDisabled = keepScreenOn
        onDispose {
            UIApplication.sharedApplication.idleTimerDisabled = false
        }
    }
}
