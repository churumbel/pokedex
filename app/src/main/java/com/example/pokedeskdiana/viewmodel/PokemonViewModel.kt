package com.example.pokedeskdiana.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.pokedeskdiana.api.RetrofitClient
import com.example.pokedeskdiana.data.FavoritePokemon
import com.example.pokedeskdiana.data.FavoritePokemonRepository
import com.example.pokedeskdiana.data.PokemonDatabase
import com.example.pokedeskdiana.model.Pokemon

class PokemonViewModel(
    application: Application,
    private val favoriteRepository: FavoritePokemonRepository) : AndroidViewModel(application) {
    //private val favoriteRepository: FavoritePokemonRepository

    // Estados
    private val _pokemon = MutableStateFlow<Pokemon?>(null)
    open val pokemon: StateFlow<Pokemon?> = _pokemon.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow(false)
    open val error: StateFlow<Boolean> = _error.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    open val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    init {
        // No se inicializa el repositorio aquí, ahora viene desde la Factory
        //val pokemonDao = PokemonDatabase.getDatabase(application).favoritePokemonDao()
        //favoriteRepository = FavoritePokemonRepository(pokemonDao)
    }

    // Función para buscar un Pokémon
    fun fetchPokemon(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = false

            try {
                val response = RetrofitClient.instance.getPokemon(name.lowercase().trim())
                _pokemon.value = response
                checkIfFavorite(response.id) // Verifica si el Pokémon está en favoritos
            } catch (e: Exception) {
                _error.value = true
                _pokemon.value = null // Asegura que no quede un Pokémon inválido en el estado
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addToFavorites(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _pokemon.value?.let { pokemon ->
                if (favoriteRepository.isFavorite(pokemon.id)) {
                    // El Pokémon ya está en favoritos
                    onResult(false)
                } else {
                    favoriteRepository.addFavorite(FavoritePokemon(pokemon.id, pokemon.name, pokemon.sprites.front_default))
                    _isFavorite.value = true
                    onResult(true)
                }
            } ?: onResult(false) // En caso de que no haya Pokémon
        }
    }

    fun removeFromFavorites() {
        viewModelScope.launch {
            _pokemon.value?.let {
                favoriteRepository.removeFavorite(FavoritePokemon(it.id, it.name, it.sprites.front_default))
                _isFavorite.value = false
            }
        }
    }

    fun checkIfFavorite(pokemonId: Int) {
        viewModelScope.launch {
            _isFavorite.value = favoriteRepository.isFavorite(pokemonId)
        }
    }
}