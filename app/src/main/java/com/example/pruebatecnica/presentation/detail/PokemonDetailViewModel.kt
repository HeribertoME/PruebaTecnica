package com.example.pruebatecnica.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnica.domain.model.Pokemon
import com.example.pruebatecnica.domain.usecases.GetPokemonByIdUseCase
import com.example.pruebatecnica.presentation.models.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonByIdUseCase: GetPokemonByIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<UiState<Pokemon>>(UiState.Loading)
    val state: StateFlow<UiState<Pokemon>> = _state

    fun getPokemonById(pokemonId: Int) {
        viewModelScope.launch {
            getPokemonByIdUseCase(pokemonId)
                .let { result ->
                    if (result != null) {
                        _state.value = UiState.Success(result)
                    } else {
                        _state.value = UiState.Error("Pokemon not found")
                    }
                }
        }
    }

}