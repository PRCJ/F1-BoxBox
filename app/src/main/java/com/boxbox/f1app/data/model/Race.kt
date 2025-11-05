package com.boxbox.f1app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RaceResponse(
    val schedule: List<Race>  // Changed from "races" to "schedule"
)

@Serializable
data class Race(
    val raceId: String,
    val circuitId: String,
    val raceName: String,
    val raceStartTime: Long,  // Changed from String to Long
    val raceEndTime: Long,    // Changed from String to Long
    val raceState: String,
    val round: Int,
    val isSprint: Boolean,
    val sessions: List<Session>,
    val podium: List<String>? = null
) {
    // Computed properties for backward compatibility
    val id: String
        get() = raceId

    val circuitName: String
        get() = circuitId.split("-").joinToString(" ") {
            it.replaceFirstChar { char -> char.uppercase() }
        }

    val location: String
        get() = when (circuitId) {
            "melbourne" -> "Melbourne"
            "shanghai" -> "Shanghai"
            "suzuka" -> "Suzuka"
            "sakhir" -> "Sakhir"
            "sao_paulo" -> "São Paulo"
            else -> circuitId
        }

    val country: String
        get() = when (circuitId) {
            "melbourne" -> "Australia"
            "shanghai" -> "China"
            "suzuka" -> "Japan"
            "sakhir" -> "Bahrain"
            "sao_paulo" -> "Brazil"
            else -> "Unknown"
        }
}