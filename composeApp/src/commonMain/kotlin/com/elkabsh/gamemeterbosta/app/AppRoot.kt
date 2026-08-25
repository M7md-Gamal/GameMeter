package com.elkabsh.gamemeterbosta.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.elkabsh.gamemeterbosta.core.ui.theme.GameMeterBostaTheme
import com.elkabsh.gamemeterbosta.feature.games.api.Route
import com.elkabsh.gamemeterbosta.feature.games.presentation.game_details.GameDetailsScreen
import com.elkabsh.gamemeterbosta.feature.games.presentation.game_details.GameDetailsViewModel
import com.elkabsh.gamemeterbosta.feature.games.presentation.games_list.GamesListScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppRoot(modifier: Modifier = Modifier) {
    GameMeterBostaTheme {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            GamesNavHost(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
private fun GamesNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.GamesList,
        modifier = modifier
    ) {
        composable<Route.GamesList> {
            GamesListScreen { id -> navController.navigate(Route.GameDetail(id)) }
        }
        composable<Route.GameDetail> {
            val id = it.toRoute<Route.GameDetail>().id
            GameDetailsScreen(
                viewModel = koinViewModel<GameDetailsViewModel>(parameters = { parametersOf(id) })
            ) { navController.navigateUp() }
        }
    }
}
