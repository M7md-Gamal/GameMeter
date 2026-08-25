package com.elkabsh.gamemeterbosta.feature.games.di

import com.elkabsh.gamemeterbosta.feature.games.presentation.game_details.GameDetailsViewModel
import com.elkabsh.gamemeterbosta.feature.games.presentation.games_list.GamesListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val gamesPresentationModule = module {
    viewModelOf(::GamesListViewModel)
    viewModelOf(::GameDetailsViewModel)
}
