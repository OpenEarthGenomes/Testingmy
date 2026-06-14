package com.example.allinonetester.utils

class TokenCounter {
    // Egyszerű közelítés: ~4 karakter = 1 token (angol szövegekhez)
    fun count(text: String): Int {
        return (text.length / 4).coerceAtLeast(1)
    }
}
