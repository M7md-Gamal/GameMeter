package com.elkabsh.gamemeterbosta.feature.games.data.remote

import com.elkabsh.gamemeterbosta.core.domain.errors.DataError
import com.elkabsh.gamemeterbosta.core.domain.Result
import com.elkabsh.gamemeterbosta.feature.games.data.remote.dto.details_dto.DetailsResponseDto
import com.elkabsh.gamemeterbosta.feature.games.data.remote.dto.list_of_games_dto.GamesListResponseDto
import com.elkabsh.gamemeterbosta.feature.games.data.remote.dto.screenshots_dto.ScreenshotsResponseDto
import com.elkabsh.gamemeterbosta.feature.games.data.remote.dto.videos_dto.VideosResponseDto

interface RemoteGameDataSource {
    suspend fun loadListOfGames(
        page: Int,
        pageSize: Int,
        category: String?
    ): Result<GamesListResponseDto, DataError.Remote>
    suspend fun loadGameDescription(id:Int): Result<DetailsResponseDto, DataError.Remote>
    suspend fun loadGameScreenshots(id:Int): Result<ScreenshotsResponseDto, DataError.Remote>
    suspend fun loadGameVideo(id:Int): Result<VideosResponseDto, DataError.Remote>

}