package com.example.pruebatecnica.data.source.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.pruebatecnica.data.source.local.db.PokemonDao
import com.example.pruebatecnica.data.source.local.db.PokemonEntity
import com.example.pruebatecnica.data.source.remote.PokemonApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val apiService: PokemonApiService,
    private val pokemonDao: PokemonDao
) : RemoteMediator<Int, PokemonEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                lastItem?.id ?: return MediatorResult.Success(endOfPaginationReached = false)
            }
        }

        try {
            val offset = page
            val response = apiService.getPokemonList(offset, state.config.pageSize)
            val pokemons = response.results.map { result ->
                val details = apiService.getPokemonDetails(result.name)
                val isFavorite = pokemonDao.isFavorite(details.id)?.isFavorite ?: false
                PokemonEntity(
                    id = details.id,
                    name = details.name,
                    imageUrl = details.sprites.front_default,
                    height = details.height,
                    weight = details.weight,
                    types = details.types.joinToString(",") { it.type.name },
                    isFavorite = isFavorite
                )
            }
            pokemonDao.insertAll(pokemons)
            return MediatorResult.Success(endOfPaginationReached = pokemons.isEmpty())
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}