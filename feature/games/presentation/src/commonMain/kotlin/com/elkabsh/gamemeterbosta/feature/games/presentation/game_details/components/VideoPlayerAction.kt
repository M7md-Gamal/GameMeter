package com.elkabsh.gamemeterbosta.feature.games.presentation.game_details.components

sealed interface VideoPlayerAction {
    data class LoadVideo(val url: String) : VideoPlayerAction
    data object TogglePlayPause : VideoPlayerAction
    data object ToggleFullscreen : VideoPlayerAction
    data object Play : VideoPlayerAction
    data object Pause : VideoPlayerAction
    data class SeekStart(val position: Float) : VideoPlayerAction
    data object SeekFinished : VideoPlayerAction
    data class SeekTo(val position: Float) : VideoPlayerAction
}
