package com.example.pruebatecnica.domain.usecases

import androidx.paging.PagingData
import com.example.pruebatecnica.data.repository.PokemonRepository
import com.example.pruebatecnica.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<PagingData<Pokemon>> {
        return repository.getPokemonList()
    }
}

class GetPokemonByIdUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(pokemonId: Int): Pokemon? {
        return repository.getPokemonById(pokemonId)
    }
}
