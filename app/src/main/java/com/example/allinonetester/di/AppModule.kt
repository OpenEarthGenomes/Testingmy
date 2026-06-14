package com.example.allinonetester.di

import android.content.Context
import com.example.allinonetester.domain.usercases.ClearRamUseCase
import com.example.allinonetester.domain.usercases.FileOperationsUseCase
import com.example.allinonetester.domain.usercases.GetFolderSizeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideGetFolderSizeUseCase(context: Context): GetFolderSizeUseCase {
        return GetFolderSizeUseCase(context)
    }

    @Provides
    @Singleton
    fun provideClearRamUseCase(context: Context): ClearRamUseCase {
        return ClearRamUseCase(context)
    }

    @Provides
    @Singleton
    fun provideFileOperationsUseCase(context: Context): FileOperationsUseCase {
        return FileOperationsUseCase(context)
    }
}
