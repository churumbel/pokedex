package com.example.pokedeskdiana.model

data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val abilities: List<Ability>,
    val sprites: Sprites,
    val stats: List<Stat>
)

data class PokemonType(
    val type : TypeDetail
)

data class TypeDetail(
    val name: String
)
data class Ability(
    val ability: AbilityDetail
)

data class AbilityDetail(
    val name: String
)

data class Sprites(
    val front_default: String
)

data class Stat(
    val base_stat: Int,
    val stat: StatDetail
)

data class StatDetail(
    val name: String
)