package com.example.pruebatecnica.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.pruebatecnica.data.mappers.toDomain
import com.example.pruebatecnica.data.source.local.LocalDataSource
import com.example.pruebatecnica.data.source.paging.PokemonRemoteMediator
import com.example.pruebatecnica.data.source.remote.RemoteDataSource
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val remoteMediator: PokemonRemoteMediator
) : PokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { localDataSource.getPokemonsPaging() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun getPokemonDetails(id: Int): Flow<Pokemon> = flow {
        val pokemon = localDataSource.getAllPokemons().first().find { it.id == id }
        if (pokemon != null) {
            emit(pokemon)
        } else {
            val details = remoteDataSource.getPokemonDetails(id.toString())
            localDataSource.insertAll(listOf(details))
            emit(details)
        }
    }

    override suspend fun updatePokemon(pokemon: Pokemon) {
        localDataSource.updatePokemon(pokemon)
    }
}