package com.example.pokedeskdiana.data

import kotlinx.coroutines.flow.Flow


class FavoritePokemonRepository(private val pokemonDao: FavoritePokemonDao) {

    fun getFavorites(): Flow<List<FavoritePokemon>> {
        return pokemonDao.getAllFavorites()
    }

    suspend fun addFavorite(pokemon: FavoritePokemon) {
        pokemonDao.addFavorite(pokemon)
    }

    suspend fun removeFavorite(pokemon: FavoritePokemon) {
        pokemonDao.removeFavorite(pokemon)
    }

    suspend fun isFavorite(pokemonId: Int): Boolean {
        return pokemonDao.getFavoriteById(pokemonId) != null
    }
}
