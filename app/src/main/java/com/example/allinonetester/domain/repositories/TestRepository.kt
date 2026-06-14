package com.example.allinonetester.domain.repositories

interface TestRepository {
    suspend fun runTest(name: String, block: suspend () -> String): String
}
