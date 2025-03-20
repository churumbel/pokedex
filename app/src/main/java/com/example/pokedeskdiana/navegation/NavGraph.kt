package com.example.pokedeskdiana.navegation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedeskdiana.screens.FavoritesScreen
import com.example.pokedeskdiana.screens.PokedexScreen
import com.example.pokedeskdiana.screens.WelcomeScreen
import com.example.pokedeskdiana.viewmodel.FavoritePokemonViewModel
import com.example.pokedeskdiana.viewmodel.PokemonViewModel

@Composable
fun NavGraph(pokemonViewModel: PokemonViewModel, favoritePokemonViewModel: FavoritePokemonViewModel) {
    val navController = rememberNavController()
    val pokemonViewModel =pokemonViewModel
    val favoritePokemonViewModel=favoritePokemonViewModel

    NavHost(navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("pokedex") { PokedexScreen(navController, pokemonViewModel) }
        composable("favorites") { FavoritesScreen(favoritePokemonViewModel, navController) }
    }
}
