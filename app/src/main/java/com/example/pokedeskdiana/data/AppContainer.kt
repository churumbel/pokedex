package com.example.pokedeskdiana.data

interface AppContainer {
    val pokemonRepository: PokemonRepository
    val favoritePokemonRepository: FavoritePokemonRepository
}