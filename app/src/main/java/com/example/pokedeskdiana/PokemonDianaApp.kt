package com.example.pokedeskdiana

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.pokedeskdiana.data.AppContainer
import com.example.pokedeskdiana.data.DefaultAppContainer
import com.example.pokedeskdiana.data.FavoritePokemonRepository
import com.example.pokedeskdiana.data.PokemonDatabase

class PokemonDianaApp : Application() {
    lateinit var container: AppContainer
    lateinit var favoritePokemonRepository : FavoritePokemonRepository

    override fun onCreate() {
        super.onCreate()
        // Obtén el FavoritePokemonDao desde la base de datos Room
        val favoritePokemonDao = PokemonDatabase.getDatabase(this).favoritePokemonDao()
        // Pasa el FavoritePokemonDao al repositorio
        favoritePokemonRepository = FavoritePokemonRepository(favoritePokemonDao)

        container = DefaultAppContainer(this)
    }
}

private const val RECORD_PREFERENCES_NAME = "record_preferences"

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = RECORD_PREFERENCES_NAME
)