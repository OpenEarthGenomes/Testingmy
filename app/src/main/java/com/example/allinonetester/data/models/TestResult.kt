package com.example.allinonetester.data.models

data class TestResult(
    val testName: String,
    val result: String,
    val timestamp: Long = System.currentTimeMillis(),
    val success: Boolean = true
) {
    fun format(): String = "[$testName]\n$result\n"
}
