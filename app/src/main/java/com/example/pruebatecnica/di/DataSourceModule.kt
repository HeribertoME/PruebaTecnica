package com.example.pruebatecnica.di

import com.example.pruebatecnica.data.repository.PokemonRepository
import com.example.pruebatecnica.data.repository.PokemonRepositoryImpl
import com.example.pruebatecnica.data.source.local.LocalDataSource
import com.example.pruebatecnica.data.source.local.LocalDataSourceImpl
import com.example.pruebatecnica.data.source.remote.RemoteDataSource
import com.example.pruebatecnica.data.source.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository
}