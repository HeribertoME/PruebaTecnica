package com.example.pruebatecnica.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pruebatecnica.domain.model.Pokemon
import com.example.pruebatecnica.domain.usecases.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    val flow: Flow<PagingData<Pokemon>> = getPokemonListUseCase().cachedIn(viewModelScope)

    /*private val _state = MutableStateFlow<UiState<List<Pokemon>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Pokemon>>> = _state.asStateFlow()

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
                .collect { pokemons ->
                    val currentList = (_state.value as? UiState.Success)?.data ?: emptyList()
                    _state.value = UiState.Success(currentList + pokemons)
                    currentPage++
                }
        }
    }*/

    fun onItemClick(pokemon: Pokemon) {
        viewModelScope.launch {
            //
        }
    }

}