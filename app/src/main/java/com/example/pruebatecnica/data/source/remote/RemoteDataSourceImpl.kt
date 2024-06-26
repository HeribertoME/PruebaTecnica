package com.example.pruebatecnica.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pruebatecnica.data.mappers.toDomain
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: PokemonApiService
) : RemoteDataSource {

    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { PokemonPagingSource(apiService) }
        ).flow
        /*val response = apiService.getPokemonList(offset, limit)
        val pokemons = response.results.map { result ->
            val details = apiService.getPokemonDetails(result.name)
            details.toDomain()
        }
        emit(pokemons)*/
    }

    override suspend fun getPokemonDetails(name: String): Pokemon {
        val details = apiService.getPokemonDetails(name)
        return details.toDomain()
    }
}