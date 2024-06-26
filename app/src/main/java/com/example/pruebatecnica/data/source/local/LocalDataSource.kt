package com.example.pruebatecnica.data.source.local

import androidx.paging.PagingSource
import com.example.pruebatecnica.data.source.local.db.PokemonEntity
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getPokemonsPaging(): PagingSource<Int, PokemonEntity>
    suspend fun getAllPokemons(): Flow<List<Pokemon>>
    suspend fun insertAll(pokemons: List<Pokemon>)
    suspend fun updatePokemon(pokemon: Pokemon)
}