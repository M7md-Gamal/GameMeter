package com.elkabsh.gamemeterbosta.feature.games.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import com.elkabsh.gamemeterbosta.core.domain.Result
import com.elkabsh.gamemeterbosta.core.domain.errors.DataError
import com.elkabsh.gamemeterbosta.feature.games.data.local.GamesDB
import com.elkabsh.gamemeterbosta.feature.games.data.local.entity.GameEntity
import com.elkabsh.gamemeterbosta.feature.games.data.local.entity.GameRemoteKeys
import com.elkabsh.gamemeterbosta.feature.games.data.mappers.toGameEntity
import com.elkabsh.gamemeterbosta.feature.games.data.mappers.toGenre
import com.elkabsh.gamemeterbosta.feature.games.data.remote.RemoteGameDataSource
import com.elkabsh.gamemeterbosta.feature.games.domain.model.GamesCategory

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator(
    private val gameApi: RemoteGameDataSource,
    private val gameDatabase: GamesDB,
    private val category: GamesCategory? = null
) : RemoteMediator<Int, GameEntity>() {

    private val gameDao = gameDatabase.gameDao()
    private val remoteKeysDao = gameDatabase.gameRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameEntity>
    ): MediatorResult {
        return run {
            try {
                val page =
                    when (loadType) {
                        LoadType.REFRESH -> {
                            1
                        }

                        LoadType.PREPEND -> {
                            return MediatorResult.Success(
                                endOfPaginationReached = true
                            )
                        }

                        LoadType.APPEND -> {
                            val remoteKeys = getRemoteKeyForLastItem(state)
                            val nextPage =
                                remoteKeys?.nextPage
                                    ?: return MediatorResult.Success(
                                        endOfPaginationReached = remoteKeys != null
                                    )
                            nextPage
                        }
                    }

                val result =
                    gameApi.loadListOfGames(
                        page = page,
                        pageSize = state.config.pageSize,
                        category = category?.toGenre()
                    )

                when (result) {
                    is Result.Success -> {
                        val response = result.data
                        val games = response.results
                        val endOfPaginationReached = games.isEmpty()

                        val safeCategory = category?.toGenre() ?: ""

                        gameDatabase.useWriterConnection { transactor ->
                            transactor.immediateTransaction {
                            if (loadType == LoadType.REFRESH) {
                                if (category != null) {
                                    remoteKeysDao.clearRemoteKeysByCategory(safeCategory)
                                    gameDao.clearGamesByCategory(safeCategory)
                                } else {
                                    remoteKeysDao.clearRemoteKeys()
                                    gameDao.clearGames()
                                }
                            }

                            val prevPage = if (page == 1) null else page - 1
                            val nextPage = if (endOfPaginationReached) null else page + 1

                            val keys =
                                games.map { game ->
                                    GameRemoteKeys(
                                        gameId = game.id,
                                        category = safeCategory,
                                        prevPage = prevPage,
                                        nextPage = nextPage
                                    )
                                }
                                remoteKeysDao.insertRemoteKeys(keys)
                                gameDao.insertGames(games.map { it.toGameEntity() })
                            }
                        }

                        MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                    }

                    is Result.Error -> {
                        MediatorResult.Error(DataErrorException(result.error))
                    }
                }
            } catch (e: Exception) {
                MediatorResult.Error(e)
            }
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, GameEntity>
    ): GameRemoteKeys? {
        val safeCategory = category?.toGenre() ?: ""
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { game ->
            remoteKeysDao.getRemoteKeys(game.id, safeCategory)
        }
    }
}

class DataErrorException(dataError: DataError) : Exception(dataError.toString())
