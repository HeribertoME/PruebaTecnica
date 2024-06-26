package com.example.pruebatecnica.di

import android.content.Context
import androidx.room.Room
import com.example.pruebatecnica.data.source.local.db.AppDatabase
import com.example.pruebatecnica.data.source.local.db.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pokemonDb"
        ).build()
    }

    @Provides
    @Singleton
    fun providePokemonDao(db: AppDatabase): PokemonDao {
        return db.pokemonDao()
    }
}