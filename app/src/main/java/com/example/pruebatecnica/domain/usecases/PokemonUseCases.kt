package com.example.pruebatecnica.domain.usecases

import com.example.pruebatecnica.data.repository.PokemonRepository
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(page: Int, limit: Int): Flow<List<Pokemon>> {
        return repository.getPokemonList(page, limit)
    }
}

class GetPokemonByIdUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId: Int): Pokemon? {
        return repository.getPokemonById(pokemonId)
    }
}

class UpdatePokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemon: Pokemon) {
        repository.updatePokemon(pokemon)
    }
}
