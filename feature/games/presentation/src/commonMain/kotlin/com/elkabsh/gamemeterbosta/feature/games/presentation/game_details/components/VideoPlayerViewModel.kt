package com.elkabsh.gamemeterbosta.feature.games.presentation.game_details.components

import androidx.lifecycle.ViewModel
import io.github.kdroidfilter.composemediaplayer.VideoPlayerState
import io.github.kdroidfilter.composemediaplayer.createVideoPlayerState

class VideoPlayerViewModel : ViewModel() {

    val playerState: VideoPlayerState = createVideoPlayerState()
    private var currentUrl: String? = null

    fun onAction(action: VideoPlayerAction) {
        when (action) {
            is VideoPlayerAction.LoadVideo -> loadVideo(action.url)
            VideoPlayerAction.TogglePlayPause -> togglePlayPause()
            VideoPlayerAction.ToggleFullscreen -> toggleFullscreen()
            VideoPlayerAction.Play -> play()
            VideoPlayerAction.Pause -> pause()
            is VideoPlayerAction.SeekStart -> seekStart(action.position)
            VideoPlayerAction.SeekFinished -> seekFinished()
            is VideoPlayerAction.SeekTo -> seekTo(action.position)
        }
    }

    fun loadVideo(url: String) {
        if (url.isNotBlank() && currentUrl != url) {
            currentUrl = url
            playerState.openUri(url)
        }
    }

    fun togglePlayPause() {
        if (playerState.isPlaying) {
            playerState.pause()
        } else {
            playerState.play()
        }
    }

    fun toggleFullscreen() {
        playerState.toggleFullscreen()
    }

    fun play() {
        playerState.play()
    }

    fun pause() {
        playerState.pause()
    }

    fun seekStart(position: Float) {
        playerState.seekStart(position)
    }

    fun seekFinished() {
        playerState.seekFinished()
    }

    fun seekTo(position: Float) {
        playerState.seekTo(position)
    }

    override fun onCleared() {
        super.onCleared()
        playerState.dispose()
    }
}
