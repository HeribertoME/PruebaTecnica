package com.example.pruebatecnica.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pruebatecnica.domain.model.Pokemon
import retrofit2.HttpException
import java.io.IOException

class PokemonPagingSource(
    private val apiService: PokemonApiService
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val position = params.key ?: 0
        return try {
            val response = apiService.getPokemonList(params.loadSize, position * params.loadSize)
            val pokemons = response.results.map { result ->
                val details = apiService.getPokemonDetails(result.name)
                Pokemon(
                    id = details.id,
                    name = details.name,
                    imageUrl = details.sprites.front_default,
                    height = details.height,
                    weight = details.weight,
                    types = details.types.map { it.type.name }
                )
            }
            LoadResult.Page(
                data = pokemons,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (pokemons.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
