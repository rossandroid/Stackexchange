package com.rossellamorgante.stackexchange.dependencyinj

import com.rossellamorgante.stackexchange.model.StackexchangeAPI
import com.rossellamorgante.stackexchange.model.StackexchangeService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL = "https://api.stackexchange.com"

    @Provides
    fun provideMenuApi(): StackexchangeAPI {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(StackexchangeAPI::class.java)
    }

    @Provides
    fun provideStackexchangeService(): StackexchangeService{
        return StackexchangeService()
    }


}