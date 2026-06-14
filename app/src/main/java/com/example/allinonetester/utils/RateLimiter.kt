package com.example.allinonetester.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

class RateLimiter(private val maxRequests: Int = 30, private val perTime: Duration = 60.seconds) {
    private val mutex = Mutex()
    private var requests = mutableListOf<Long>()
    private val _remaining = MutableStateFlow(maxRequests)
    val remaining = _remaining.asStateFlow()

    suspend fun tryAcquire(): Boolean = mutex.withLock {
        val now = TimeSource.Monotonic.markNow().elapsedNow().inWholeMilliseconds
        requests.removeAll { now - it > perTime.inWholeMilliseconds }
        if (requests.size < maxRequests) {
            requests.add(now)
            _remaining.value = maxRequests - requests.size
            true
        } else {
            false
        }
    }
}

