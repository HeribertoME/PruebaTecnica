package com.example.pruebatecnica.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnica.domain.model.Pokemon
import com.example.pruebatecnica.domain.usecases.GetPokemonListUseCase
import com.example.pruebatecnica.domain.usecases.UpdatePokemonUseCase
import com.example.pruebatecnica.presentation.models.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val updatePokemonUseCase: UpdatePokemonUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<Pokemon>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Pokemon>>> = _state.asStateFlow()

    private var currentPage = 0
    private val limit = 25

    init {
        loadPokemonList()
    }

    fun loadPokemonList() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            getPokemonListUseCase(currentPage, limit)
                .catch { exception ->
                    _state.value = UiState.Error(exception.message ?: "Unknown error")
                }
                .collect { pokes ->
                    _state.value = UiState.Success(pokes)
                    currentPage++
                }
        }
    }

    fun onFavoriteClick(pokemon: Pokemon) {
        viewModelScope.launch {
            val updatedPokemon = pokemon.copy(isFavorite = !pokemon.isFavorite)
            updatePokemonUseCase(updatedPokemon)
        }
    }

}