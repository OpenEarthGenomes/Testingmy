package com.example.allinonetester.di

import com.example.allinonetester.domain.usercases.GetFolderSizeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGetFolderSizeUseCase(): GetFolderSizeUseCase {
        return GetFolderSizeUseCase()
    }
}
