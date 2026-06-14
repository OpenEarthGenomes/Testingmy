package com.example.allinonetester.di

import android.content.Context
import com.example.allinonetester.domain.usercases.*
import com.example.allinonetester.utils.*
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
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideCacheManager(context: Context): CacheManager = CacheManager(context)

    @Provides
    @Singleton
    fun provideRateLimiter(): RateLimiter = RateLimiter()

    @Provides
    @Singleton
    fun provideTokenCounter(): TokenCounter = TokenCounter()

    @Provides @Singleton fun provideCheckInternetUseCase(context: Context) = CheckInternetUseCase(context)
    @Provides @Singleton fun provideDnsLookupUseCase() = DnsLookupUseCase()
    @Provides @Singleton fun provideGetPublicIpUseCase() = GetPublicIpUseCase()
    @Provides @Singleton fun provideGetResponseTimeUseCase() = GetResponseTimeUseCase()
    @Provides @Singleton fun provideSpeedTestUseCase() = SpeedTestUseCase()
    @Provides @Singleton fun providePingUseCase() = PingUseCase()
    @Provides @Singleton fun providePortScanUseCase() = PortScanUseCase()
    @Provides @Singleton fun provideGetFolderSizeUseCase() = GetFolderSizeUseCase()
    @Provides @Singleton fun provideGetBatteryStatusUseCase(context: Context) = GetBatteryStatusUseCase(context)
    @Provides @Singleton fun provideGetNetworkStateUseCase(context: Context) = GetNetworkStateUseCase(context)
    @Provides @Singleton fun provideGetRamInfoUseCase() = GetRamInfoUseCase()
    @Provides @Singleton fun provideGetStorageInfoUseCase() = GetStorageInfoUseCase()
    @Provides @Singleton fun provideGetDeviceInfoUseCase() = GetDeviceInfoUseCase()
    @Provides @Singleton fun provideGetScreenBrightnessUseCase(context: Context) = GetScreenBrightnessUseCase(context)
    @Provides @Singleton fun provideGetCpuCoreCountUseCase() = GetCpuCoreCountUseCase()
    @Provides @Singleton fun provideListInstalledAppsUseCase(context: Context) = ListInstalledAppsUseCase(context)
    @Provides @Singleton fun provideGetMobileNetworkTypeUseCase(context: Context) = GetMobileNetworkTypeUseCase(context)
    @Provides @Singleton fun provideListSensorsUseCase(context: Context) = ListSensorsUseCase(context)
    @Provides @Singleton fun provideGetDisplayInfoUseCase(context: Context) = GetDisplayInfoUseCase(context)
    @Provides @Singleton fun provideGetSecurityPatchUseCase() = GetSecurityPatchUseCase()
    @Provides @Singleton fun provideIsRootedUseCase() = IsRootedUseCase()
}

