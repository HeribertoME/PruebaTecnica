package com.example.pruebatecnica.data.source.remote.responses

data class PokemonResult(
    val name: String,
    val url: String
) {
    val id: Int

    init {
        id = extractIdFromUrl(url)
    }

    private fun extractIdFromUrl(url: String): Int {
        val regex = """.*/(\d+)/$""".toRegex()
        val matchResult = regex.find(url)
        return matchResult?.groupValues?.get(1)?.toInt() ?: throw IllegalArgumentException("Invalid URL format")
    }
}