package com.example.pokedeskdiana.api

import com.example.pokedeskdiana.data.PokemonRepository
import com.example.pokedeskdiana.model.Pokemon

class ApiPokemonRepository(private val pokeApiService: PokeApiService) : PokemonRepository {
    override suspend fun getPokemonByName(name: String): Pokemon? {
        return try {
            pokeApiService.getPokemon(name)
        } catch (e: Exception) {
            null // Manejo de errores
        }
    }
}