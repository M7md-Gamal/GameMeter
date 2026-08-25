package com.elkabsh.gamemeterbosta.feature.games.presentation.games_list

import com.elkabsh.gamemeterbosta.feature.games.domain.model.GamesCategory

sealed interface GamesListAction {
    data class UpdateSearchQuery(val query: String) : GamesListAction
    data class SelectCategory(val category: GamesCategory) : GamesListAction
    data class NavigateToDetails(val gameId: Int) : GamesListAction
    object Retry : GamesListAction
    object DismissError : GamesListAction
}

sealed class GamesListUIEvent {
    data class NavigateToDetails(val gameId: Int) : GamesListUIEvent()
}
