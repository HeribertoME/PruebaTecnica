package com.example.pruebatecnica.data.source.remote.responses

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<PokemonResult>
)