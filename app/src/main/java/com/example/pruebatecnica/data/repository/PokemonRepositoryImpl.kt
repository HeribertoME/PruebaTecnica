package com.example.pruebatecnica.data.repository

import androidx.paging.PagingData
import com.example.pruebatecnica.data.source.local.LocalDataSource
import com.example.pruebatecnica.data.source.remote.RemoteDataSource
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : PokemonRepository {

    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return remoteDataSource.getPokemonList()
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