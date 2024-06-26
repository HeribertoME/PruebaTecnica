package com.example.pruebatecnica.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pruebatecnica.data.mappers.toDomain
import com.example.pruebatecnica.data.source.paging.PokemonPagingSource
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
    }

    override suspend fun getPokemonDetails(name: String): Pokemon {
        val details = apiService.getPokemonDetails(name)
        return details.toDomain()
    }
}