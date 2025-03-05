package com.example.pokedeskdiana.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedeskdiana.data.FavoritePokemon
import com.example.pokedeskdiana.data.PokemonDatabase
import com.example.pokedeskdiana.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class FavoritePokemonViewModel(application: Application) : AndroidViewModel(application) {
    private val pokemonDao = PokemonDatabase.getDatabase(application).favoritePokemonDao()

    private val _favorites = MutableStateFlow<List<FavoritePokemon>>(emptyList()) // Estado interno
    val favorites: StateFlow<List<FavoritePokemon>> = _favorites.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            pokemonDao.getAllFavorites().collect { favoriteList ->
                _favorites.value = favoriteList
            }
        }
    }

    fun addToFavorites(pokemon: FavoritePokemon) {
        viewModelScope.launch {
            pokemonDao.addFavorite(pokemon)
            checkIfFavorite(pokemon.id)
        }
    }

    fun removeFromFavorites(pokemon: FavoritePokemon) {
        viewModelScope.launch {
            pokemonDao.removeFavorite(pokemon)
            checkIfFavorite(pokemon.id)
        }
    }

    fun checkIfFavorite(pokemonId: Int) {
        viewModelScope.launch {
            _isFavorite.value = _favorites.value.any { it.id == pokemonId }
        }
    }
}
