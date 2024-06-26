package com.example.pruebatecnica.data.repository

import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonList(page: Int, limit: Int): Flow<List<Pokemon>>

    suspend fun getPokemonDetails(id: Int): Flow<Pokemon>

    suspend fun updatePokemon(pokemon: Pokemon)
}