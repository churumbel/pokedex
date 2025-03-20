package com.example.pokedeskdiana

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokedeskdiana.data.FavoritePokemonRepository
import com.example.pokedeskdiana.data.PokemonDatabase
import com.example.pokedeskdiana.navegation.NavGraph
import com.example.pokedeskdiana.ui.theme.PokedeskDianaTheme
import com.example.pokedeskdiana.screens.PokedexScreen
import com.example.pokedeskdiana.viewmodel.FavoritePokemonViewModel
import com.example.pokedeskdiana.viewmodel.FavoritePokemonViewModelFactory
import com.example.pokedeskdiana.viewmodel.PokemonViewModel
import com.example.pokedeskdiana.viewmodel.PokemonViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application
        val favoriteRepository = FavoritePokemonRepository(PokemonDatabase.getDatabase(application).favoritePokemonDao())

        val pokemonViewModel: PokemonViewModel by viewModels {
            PokemonViewModelFactory(application, favoriteRepository)
        }
        val favoritePokemonViewModel: FavoritePokemonViewModel by viewModels {
            FavoritePokemonViewModelFactory(application)
        }

        setContent {
            PokedeskDianaTheme {
                NavGraph(pokemonViewModel, favoritePokemonViewModel)
            }

        }
    }
}
