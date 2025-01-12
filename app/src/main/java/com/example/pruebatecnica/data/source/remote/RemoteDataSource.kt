package com.example.pruebatecnica.data.source.remote

import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getPokemonList(offset: Int, limit: Int): Flow<List<Pokemon>>
    suspend fun getPokemonDetails(name: String): Pokemon
}