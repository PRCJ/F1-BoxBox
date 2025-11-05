package com.boxbox.f1app.domain.model

data class CircuitDetail(
    val circuitName: String,
    val location: String,
    val country: String,
    val firstGrandPrix: String,
    val numberOfLaps: Int,
    val circuitLength: String,
    val raceDistance: String,
    val lapRecord: LapRecord,
    val facts: List<String>
)

data class LapRecord(
    val time: String,
    val driver: String,
    val year: Int
)

// Static circuit data repository
object CircuitData {

    fun getCircuitDetails(circuitName: String): CircuitDetail {
        // Default data - in real app, this would come from a database or API
        return when {
            circuitName.contains("Monaco", ignoreCase = true) -> CircuitDetail(
                circuitName = "Circuit de Monaco",
                location = "Monte Carlo",
                country = "Monaco",
                firstGrandPrix = "1950",
                numberOfLaps = 78,
                circuitLength = "3.337 km",
                raceDistance = "260.286 km",
                lapRecord = LapRecord("1:12.909", "Lewis Hamilton", 2021),
                facts = listOf(
                    "The Monaco Grand Prix is one of the most prestigious races in Formula 1",
                    "It's the slowest circuit on the F1 calendar",
                    "The track runs through the streets of Monte Carlo",
                    "Overtaking is extremely difficult due to the narrow streets"
                )
            )
            circuitName.contains("Silverstone", ignoreCase = true) -> CircuitDetail(
                circuitName = "Silverstone Circuit",
                location = "Silverstone",
                country = "United Kingdom",
                firstGrandPrix = "1950",
                numberOfLaps = 52,
                circuitLength = "5.891 km",
                raceDistance = "306.198 km",
                lapRecord = LapRecord("1:27.097", "Max Verstappen", 2020),
                facts = listOf(
                    "Home of British motorsport",
                    "Hosted the very first Formula 1 World Championship race in 1950",
                    "Features some of the fastest corners in F1",
                    "Known for its unpredictable British weather"
                )
            )
            else -> CircuitDetail(
                circuitName = circuitName,
                location = "Unknown",
                country = "Unknown",
                firstGrandPrix = "N/A",
                numberOfLaps = 0,
                circuitLength = "N/A",
                raceDistance = "N/A",
                lapRecord = LapRecord("N/A", "Unknown", 0),
                facts = listOf(
                    "This is an exciting Formula 1 circuit",
                    "More details coming soon"
                )
            )
        }
    }
}