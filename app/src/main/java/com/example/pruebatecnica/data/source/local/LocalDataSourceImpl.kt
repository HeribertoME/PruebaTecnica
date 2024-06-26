package com.example.pruebatecnica.data.source.local

import com.example.pruebatecnica.data.mappers.toDomain
import com.example.pruebatecnica.data.mappers.toEntity
import com.example.pruebatecnica.data.source.local.db.PokemonDao
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
) : LocalDataSource {

    override suspend fun getAllPokemons(): Flow<List<Pokemon>> {
        return pokemonDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertAll(pokemons: List<Pokemon>) {
        pokemonDao.insertAll(pokemons.map { it.toEntity() })
    }

    override suspend fun updatePokemon(pokemon: Pokemon) {
        pokemonDao.update(pokemon.toEntity())
    }
}