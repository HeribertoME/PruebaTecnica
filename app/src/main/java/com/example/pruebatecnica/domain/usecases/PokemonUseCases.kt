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
/*
class GetPokemonDetailUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: Int): Pokemon {
        return repository.getPokemonDetail(id)
    }
}

class InsertPokemonUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(pokemon: Pokemon) {
        repository.insertPokemon(pokemon)
    }
}

class UpdatePokemonUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(pokemon: Pokemon) {
        repository.updatePokemon(pokemon)
    }
}

class DeletePokemonUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(pokemon: Pokemon) {
        repository.deletePokemon(pokemon)
    }
}

class GetAllPokemonUseCase(private val repository: PokemonRepository) {
    operator fun invoke(): LiveData<List<Pokemon>> {
        return repository.getAllPokemon()
    }
}*/