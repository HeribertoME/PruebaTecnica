package com.example.pruebatecnica.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pruebatecnica.domain.model.Pokemon
import com.example.pruebatecnica.domain.usecases.GetPokemonListUseCase
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
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<PagingData<Pokemon>>>(UiState.Loading)
    val state: StateFlow<UiState<PagingData<Pokemon>>> = _state.asStateFlow()

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            getPokemonListUseCase()
                .cachedIn(viewModelScope)
                .catch { exception ->
                    _state.value = UiState.Error(exception.message ?: "Unknown error")
                }
                .collectLatest { pagingData ->
                    _state.value = UiState.Success(pagingData)
                }
        }
    }

    fun onItemClick(pokemon: Pokemon) {
        viewModelScope.launch {
            //
        }
    }

}