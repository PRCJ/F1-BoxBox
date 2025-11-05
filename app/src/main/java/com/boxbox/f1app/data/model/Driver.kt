package com.boxbox.f1app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DriverResponse(
    val drivers: List<Driver>
)

@Serializable
data class Driver(
    val position: Int,
    val firstName: String,
    val lastName: String,
    val teamName: String,
    val points: Int,
    val racingNumber: Int? = null,
    val driverCode: String? = null,
    val driverId: String? = null,
    val teamId: String? = null,
    val wins: Int? = null,
    val podiums: Int? = null,
    val poles: Int? = null
) {
    // Computed properties for backward compatibility
    val name: String
        get() = "$firstName $lastName"

    val team: String
        get() = teamName

    val carNumber: Int?
        get() = racingNumber
}