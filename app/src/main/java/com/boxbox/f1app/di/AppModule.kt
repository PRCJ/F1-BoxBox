package com.boxbox.f1app.di

import com.boxbox.f1app.data.remote.ApiService
import com.boxbox.f1app.data.remote.KtorClient
import com.boxbox.f1app.data.repository.DriverRepository
import com.boxbox.f1app.data.repository.RaceRepository
import com.boxbox.f1app.domain.usecase.GetTopDriverUseCase
import com.boxbox.f1app.domain.usecase.GetUpcomingRaceUseCase

object AppModule {

    private val ktorClient by lazy { KtorClient() }

    private val apiService by lazy {
        ApiService(ktorClient.client)
    }

    val driverRepository by lazy {
        DriverRepository(apiService)
    }

    val raceRepository by lazy {
        RaceRepository(apiService)
    }

    val getTopDriverUseCase by lazy {
        GetTopDriverUseCase(driverRepository)
    }

    val getUpcomingRaceUseCase by lazy {
        GetUpcomingRaceUseCase(raceRepository)
    }
}