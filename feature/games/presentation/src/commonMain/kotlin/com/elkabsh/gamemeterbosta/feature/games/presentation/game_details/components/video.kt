package com.elkabsh.gamemeterbosta.feature.games.presentation.game_details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreOwner
import coil3.compose.SubcomposeAsyncImage
import com.elkabsh.gamemeterbosta.core.ui.theme.GameMeterBostaTheme
import io.github.kdroidfilter.composemediaplayer.PreviewableVideoPlayerState
import io.github.kdroidfilter.composemediaplayer.VideoPlayerState
import io.github.kdroidfilter.composemediaplayer.VideoPlayerSurface
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier,
    thumbnailUrl: String? = null,
    viewModel: VideoPlayerViewModel = koinViewModel(
        viewModelStoreOwner = rememberViewModelStoreOwner()
    )
) {
    var isPlayingVideo by rememberSaveable(videoUrl) { mutableStateOf(thumbnailUrl == null) }

    LaunchedEffect(videoUrl, isPlayingVideo) {
        if (isPlayingVideo) {
            viewModel.onAction(VideoPlayerAction.LoadVideo(videoUrl))
        }
    }

    Crossfade(
        targetState = isPlayingVideo,
        modifier = modifier.fillMaxWidth()
    ) { showVideo ->
        if (showVideo) {
            VideoPlayerContent(
                playerState = viewModel.playerState,
                onToggleFullscreen = { viewModel.onAction(VideoPlayerAction.ToggleFullscreen) },
                onPlayPause = { viewModel.onAction(VideoPlayerAction.TogglePlayPause) },
                onSeekStart = { viewModel.onAction(VideoPlayerAction.SeekStart(it)) },
                onSeekFinished = { viewModel.onAction(VideoPlayerAction.SeekFinished) },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (thumbnailUrl != null) {
                    SubcomposeAsyncImage(
                        model = thumbnailUrl,
                        contentDescription = "Video Thumbnail",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        loading = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.size(48.dp), strokeWidth = 2.dp)
                            }
                        },
                        error = {
                            Icon(imageVector = Icons.Default.BrokenImage, contentDescription = null)
                        }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                )

                IconButton(
                    onClick = {
                        isPlayingVideo = true
                        viewModel.onAction(VideoPlayerAction.LoadVideo(videoUrl))
                        viewModel.onAction(VideoPlayerAction.Play)
                    },
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = Color.White.copy(alpha = 0.8f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play Video",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPlayerContent(
    playerState: VideoPlayerState,
    onToggleFullscreen: () -> Unit,
    onPlayPause: () -> Unit,
    onSeekStart: (Float) -> Unit,
    onSeekFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    KeepScreenOn(keepScreenOn = playerState.isPlaying)

    var areControlsVisible by remember { mutableStateOf(true) }

    LaunchedEffect(areControlsVisible, playerState.isPlaying, playerState.userDragging) {
        if (areControlsVisible && playerState.isPlaying && !playerState.userDragging) {
            delay(2000)
            areControlsVisible = false
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        VideoPlayerSurface(
            playerState = playerState,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        areControlsVisible = !areControlsVisible
                    }
            ) {
                if (playerState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }

                AnimatedVisibility(
                    visible = areControlsVisible,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                                )
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                areControlsVisible = true
                            }
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onPlayPause) {
                            Icon(
                                imageVector = if (playerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (playerState.isPlaying) "Pause" else "Play",
                                tint = Color.White
                            )
                        }

                        Slider(
                            value = playerState.sliderPos.coerceIn(0f, 1000f),
                            onValueChange = onSeekStart,
                            onValueChangeFinished = onSeekFinished,
                            valueRange = 0f..1000f,
                            modifier = Modifier.weight(1f),
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.primary,
                                inactiveTrackColor = Color.White.copy(alpha = 0.4f)
                            )
                        )

                        val currentTime = playerState.positionText.ifBlank { "00:00" }
                        val totalDuration = playerState.durationText.ifBlank { "00:00" }
                        Text(
                            text = "$currentTime / $totalDuration",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        IconButton(onClick = onToggleFullscreen) {
                            Icon(
                                imageVector = if (playerState.isFullscreen) Icons.Default.FullscreenExit else Icons.Default.Fullscreen,
                                contentDescription = if (playerState.isFullscreen) "Exit Fullscreen" else "Enter Fullscreen",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun VideoPlayerPreview() {
    GameMeterBostaTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            VideoPlayerContent(
                playerState = PreviewableVideoPlayerState(),
                onToggleFullscreen = {},
                onPlayPause = {},
                onSeekStart = {},
                onSeekFinished = {}
            )
        }
    }
}