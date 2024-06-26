package com.example.pruebatecnica.data.source.remote

import com.example.pruebatecnica.data.mappers.toDomain
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: PokemonApiService
) : RemoteDataSource {

    override suspend fun getPokemonList(limit: Int, offset: Int): Flow<List<Pokemon>> = flow {
        val response = apiService.getPokemonList(limit, offset)
        val pokemons = response.results.map { result ->
            val details = apiService.getPokemonDetails(result.name)
            details.toDomain()
        }
        emit(pokemons)
    }

    override suspend fun getPokemonDetails(name: String): Pokemon {
        val details = apiService.getPokemonDetails(name)
        return details.toDomain()
    }
}