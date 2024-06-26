package com.example.pruebatecnica.data.repository

import androidx.paging.PagingData
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemonList(): Flow<PagingData<Pokemon>>

    suspend fun getPokemonDetails(id: Int): Flow<Pokemon>

    suspend fun updatePokemon(pokemon: Pokemon)
}