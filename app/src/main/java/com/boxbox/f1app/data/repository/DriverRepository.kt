package com.boxbox.f1app.data.repository

import com.boxbox.f1app.data.model.Driver
import com.boxbox.f1app.data.remote.ApiService
import com.boxbox.f1app.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DriverRepository(private val apiService: ApiService) {

    suspend fun getTopDriver(): Result<Driver> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getDrivers()
            val topDriver = response.drivers.firstOrNull { it.position == 1 }

            if (topDriver != null) {
                Result.Success(topDriver)
            } else {
                Result.Error(Exception("No driver at position 1 found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getAllDrivers(): Result<List<Driver>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getDrivers()
            Result.Success(response.drivers)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}