package com.example.pruebatecnica.data.source.local

import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun getAllPokemons(): Flow<List<Pokemon>>
    suspend fun insertAll(pokemons: List<Pokemon>)
    suspend fun updatePokemon(pokemon: Pokemon)
}