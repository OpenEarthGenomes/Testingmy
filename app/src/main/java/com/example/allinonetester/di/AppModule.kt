package com.example.allinonetester.di

import android.content.Context
import com.example.allinonetester.domain.usercases.*
import com.example.allinonetester.utils.CacheManager
import com.example.allinonetester.utils.RateLimiter
import com.example.allinonetester.utils.TokenCounter
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
    fun provideCacheManager(@ApplicationContext context: Context): CacheManager = CacheManager(context)

    @Provides
    @Singleton
    fun provideRateLimiter(): RateLimiter = RateLimiter()

    @Provides
    @Singleton
    fun provideTokenCounter(): TokenCounter = TokenCounter()

    // Itt a Context-et igénylő UseCase-ek Factory-jai
    @Provides
    fun provideCheckInternetUseCase(@ApplicationContext context: Context) = CheckInternetUseCase(context)

    @Provides
    fun provideGetBatteryStatusUseCase(@ApplicationContext context: Context) = GetBatteryStatusUseCase(context)

    @Provides
    fun provideGetNetworkStateUseCase(@ApplicationContext context: Context) = GetNetworkStateUseCase(context)

    @Provides
    fun provideGetScreenBrightnessUseCase(@ApplicationContext context: Context) = GetScreenBrightnessUseCase(context)

    @Provides
    fun provideListInstalledAppsUseCase(@ApplicationContext context: Context) = ListInstalledAppsUseCase(context)

    @Provides
    fun provideGetMobileNetworkTypeUseCase(@ApplicationContext context: Context) = GetMobileNetworkTypeUseCase(context)

    @Provides
    fun provideListSensorsUseCase(@ApplicationContext context: Context) = ListSensorsUseCase(context)

    @Provides
    fun provideGetDisplayInfoUseCase(@ApplicationContext context: Context) = GetDisplayInfoUseCase(context)
    
    @Provides
    fun provideGetFolderSizeUseCase(@ApplicationContext context: Context) = GetFolderSizeUseCase(context)

    // A Context nélküli UseCase-ek
    @Provides
    fun provideDnsLookupUseCase() = DnsLookupUseCase()

    @Provides
    fun provideGetPublicIpUseCase() = GetPublicIpUseCase()

    @Provides
    fun provideGetResponseTimeUseCase() = GetResponseTimeUseCase()

    @Provides
    fun provideSpeedTestUseCase() = SpeedTestUseCase()

    @Provides
    fun providePingUseCase() = PingUseCase()

    @Provides
    fun providePortScanUseCase() = PortScanUseCase()

    @Provides
    fun provideGetRamInfoUseCase() = GetRamInfoUseCase()

    @Provides
    fun provideGetStorageInfoUseCase() = GetStorageInfoUseCase()

    @Provides
    fun provideGetDeviceInfoUseCase() = GetDeviceInfoUseCase()

    @Provides
    fun provideGetCpuCoreCountUseCase() = GetCpuCoreCountUseCase()

    @Provides
    fun provideGetSecurityPatchUseCase() = GetSecurityPatchUseCase()

    @Provides
    fun provideIsRootedUseCase() = IsRootedUseCase()
}

