package com.example.pokedeskdiana.data

import android.content.Context
import com.example.pokedeskdiana.api.PokeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DefaultAppContainer(context: Context) : AppContainer {
    private val baseUrl = "https://pokeapi.co/api/v2/"

    // Configuración de Retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val pokeApiService: PokeApiService by lazy {
        retrofit.create(PokeApiService::class.java)
    }

    override val pokemonRepository: PokemonRepository by lazy {
        NetworkPokemonRepository(pokeApiService) // Implementación que usa la API
    }

    // Configuración de Room
    private val appDatabase: PokemonDatabase = PokemonDatabase.getDatabase(context)
    private val favoritePokemonDao = appDatabase.favoritePokemonDao()

    override val favoritePokemonRepository: FavoritePokemonRepository by lazy {
        FavoritePokemonRepository(favoritePokemonDao)
    }
}