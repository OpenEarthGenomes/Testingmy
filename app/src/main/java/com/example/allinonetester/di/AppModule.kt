package com.example.allinonetester.di

import com.example.allinonetester.utils.CacheManager
import com.example.allinonetester.utils.RateLimiter
import com.example.allinonetester.utils.TokenCounter

object AppModule {
    private val cacheManager = CacheManager()
    private val rateLimiter = RateLimiter()
    private val tokenCounter = TokenCounter()
    
    fun provideCacheManager(): CacheManager = cacheManager
    fun provideRateLimiter(): RateLimiter = rateLimiter
    fun provideTokenCounter(): TokenCounter = tokenCounter
}
