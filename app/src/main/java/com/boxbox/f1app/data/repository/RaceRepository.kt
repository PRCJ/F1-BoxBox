package com.boxbox.f1app.data.repository

import com.boxbox.f1app.data.model.Race
import com.boxbox.f1app.data.remote.ApiService
import com.boxbox.f1app.util.DateTimeUtil
import com.boxbox.f1app.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RaceRepository(private val apiService: ApiService) {

    suspend fun getUpcomingRace(): Result<Race> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getRaces()
            // Changed from response.races to response.schedule
            val upcomingRace = response.schedule.firstOrNull { race ->
                DateTimeUtil.isUpcoming(race.raceStartTime, race.raceEndTime)
            }

            if (upcomingRace != null) {
                Result.Success(upcomingRace)
            } else {
                Result.Error(Exception("No upcoming race found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getAllRaces(): Result<List<Race>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getRaces()
            // Changed from response.races to response.schedule
            Result.Success(response.schedule)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}