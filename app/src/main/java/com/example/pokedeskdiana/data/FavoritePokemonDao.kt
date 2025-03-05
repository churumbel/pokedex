package com.example.pokedeskdiana.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(pokemon: FavoritePokemon)

    @Delete
    suspend fun removeFavorite(pokemon: FavoritePokemon)

    @Query("SELECT * FROM favorite_pokemon")
    fun getAllFavorites(): Flow<List<FavoritePokemon>>

    @Query("SELECT * FROM favorite_pokemon WHERE id = :pokemonId LIMIT 1")
    suspend fun getFavoriteById(pokemonId: Int): FavoritePokemon?
}
