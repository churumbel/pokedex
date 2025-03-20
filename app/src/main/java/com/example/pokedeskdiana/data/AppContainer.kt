package com.example.pokedeskdiana.data

import com.example.pokedeskdiana.model.Pokemon

interface AppContainer {
    val pokemonRepository: PokemonRepository
    val favoritePokemonRepository: FavoritePokemonRepository
}


interface PokemonRepository {
    suspend fun getPokemonByName(name: String): Pokemon?
    // Otros métodos según sea necesario
}