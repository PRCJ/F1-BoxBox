package com.boxbox.f1app.domain.usecase

import com.boxbox.f1app.data.model.Driver
import com.boxbox.f1app.data.repository.DriverRepository
import com.boxbox.f1app.util.Result

class GetTopDriverUseCase(private val repository: DriverRepository) {

    suspend operator fun invoke(): Result<Driver> {
        return repository.getTopDriver()
    }
}