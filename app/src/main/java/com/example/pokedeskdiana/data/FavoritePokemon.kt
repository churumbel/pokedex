package com.example.pokedeskdiana.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemon")
data class FavoritePokemon(
    @PrimaryKey val id: Int,
    val name: String,
    val spriteUrl: String
)
