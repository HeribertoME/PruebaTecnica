package com.example.pruebatecnica.utils

object Utils {

    fun extractInitials(text: String?): String {
        val words = text?.trim()?.split("\\s+".toRegex())?.take(2) ?: emptyList()
        return words.joinToString("") { it[0].uppercase() }
    }
}