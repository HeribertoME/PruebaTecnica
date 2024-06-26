package com.example.pruebatecnica.data.mappers

import com.example.pruebatecnica.data.source.local.db.PokemonEntity
import com.example.pruebatecnica.data.source.remote.responses.PokemonDetailResponse
import com.example.pruebatecnica.domain.model.Pokemon

fun PokemonDetailResponse.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = sprites.front_default,
        height = height,
        weight = weight,
        types = types.map { it.type.name }
    )
}

fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        imageUrl = imageUrl,
        height = height,
        weight = weight,
        types = types.split(","),
        isFavorite = isFavorite
    )
}

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        height = height,
        weight = weight,
        types = types.joinToString(","),
        isFavorite = isFavorite
    )
}