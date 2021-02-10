package com.sixt.car_api.di

import com.sixt.car_api.data.repository.SixtRepository
import com.sixt.car_api.data.service.CarApi
import com.sixt.car_api.data.service.getDefaultApi
import com.sixt.car_api.mapper.CarMapper
import com.sixt.domain.data.entity.MainConfig
import com.sixt.domain.data.entity.carApiUrl
import com.sixt.domain.data.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CarModule {

    @Provides
    @Singleton
    fun provideMainConfig(): MainConfig {
        return MainConfig("ihgfg098")
    }

    @Provides
    fun provideCarApi(mainConfig: MainConfig): CarApi {
        return getDefaultApi(mainConfig.carApiUrl, mainConfig.sixtApiKey)
    }

    @Provides
    fun provideTenorMapper(): CarMapper {
        return CarMapper()
    }

    @Provides
    @Singleton
    fun provideCarRepository(api: CarApi, mapper: CarMapper): CarRepository {
        return SixtRepository(api,mapper)
    }
}