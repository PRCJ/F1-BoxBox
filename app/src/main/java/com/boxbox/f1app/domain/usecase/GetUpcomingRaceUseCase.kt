package com.boxbox.f1app.domain.usecase

import com.boxbox.f1app.data.model.Race
import com.boxbox.f1app.data.repository.RaceRepository
import com.boxbox.f1app.util.Result

class GetUpcomingRaceUseCase(private val repository: RaceRepository) {

    suspend operator fun invoke(): Result<Race> {
        return repository.getUpcomingRace()
    }
}