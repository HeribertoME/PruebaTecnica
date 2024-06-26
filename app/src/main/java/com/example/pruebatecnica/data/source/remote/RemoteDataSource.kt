package com.example.pruebatecnica.data.source.remote

import androidx.paging.PagingData
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getPokemonList(): Flow<PagingData<Pokemon>>
    suspend fun getPokemonDetails(name: String): Pokemon
}