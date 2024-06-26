package com.example.pruebatecnica.data.source.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAll(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon")
    fun getAllPaging(): PagingSource<Int, PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon: List<PokemonEntity>)

    @Update
    suspend fun update(pokemon: PokemonEntity)

    @Query("SELECT * FROM pokemon WHERE id = :pokemonId LIMIT 1")
    suspend fun getPokemonById(pokemonId: Int): PokemonEntity?
}