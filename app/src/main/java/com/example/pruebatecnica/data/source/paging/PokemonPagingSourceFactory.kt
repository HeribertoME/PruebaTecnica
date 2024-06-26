package com.example.pruebatecnica.data.source.paging

import androidx.paging.PagingSource
import com.example.pruebatecnica.data.source.remote.PokemonApiService
import com.example.pruebatecnica.domain.model.Pokemon
import javax.inject.Inject

class PokemonPagingSourceFactory @Inject constructor(
    private val apiService: PokemonApiService
) {

    fun create(): PagingSource<Int, Pokemon> {
        return PokemonPagingSource(apiService)
    }

}
