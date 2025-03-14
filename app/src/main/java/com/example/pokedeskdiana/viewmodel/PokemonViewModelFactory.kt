package com.example.pokedeskdiana.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokedeskdiana.data.FavoritePokemonRepository

class PokemonViewModelFactory (private val application: Application,
                               private val favoriteRepository: FavoritePokemonRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokemonViewModel(application, favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}