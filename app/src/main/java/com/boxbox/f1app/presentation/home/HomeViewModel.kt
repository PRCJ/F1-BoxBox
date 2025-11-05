package com.boxbox.f1app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.f1app.data.model.Driver
import com.boxbox.f1app.data.model.Race
import com.boxbox.f1app.data.model.Session
import com.boxbox.f1app.di.AppModule
import com.boxbox.f1app.util.DateTimeUtil
import com.boxbox.f1app.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant

data class HomeUiState(
    val topDrivers: List<Driver> = emptyList(),
    val upcomingRace: Race? = null,
    val nextSession: Session? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class HomeViewModel : ViewModel() {

    private val getTopDriverUseCase = AppModule.getTopDriverUseCase
    private val getUpcomingRaceUseCase = AppModule.getUpcomingRaceUseCase

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            try {
                // Load top driver
                when (val driverResult = getTopDriverUseCase()) {
                    is Result.Success -> {
                        _uiState.value = _uiState.value.copy(
                            topDrivers = listOf(driverResult.data)
                        )
                    }
                    is Result.Error -> {
                        // Use mock data as fallback
                        _uiState.value = _uiState.value.copy(
                            topDrivers = listOf(getMockDriver()),
                            error = "Using demo driver data"
                        )
                    }
                    else -> {}
                }

                // Load upcoming race
                when (val raceResult = getUpcomingRaceUseCase()) {
                    is Result.Success -> {
                        val race = raceResult.data
                        val nextSession = DateTimeUtil.getNextSession(race.sessions)

                        _uiState.value = _uiState.value.copy(
                            upcomingRace = race,
                            nextSession = nextSession,
                            isLoading = false
                        )
                    }
                    is Result.Error -> {
                        // Use mock data as fallback
                        val mockRace = getMockRace()
                        val nextSession = DateTimeUtil.getNextSession(mockRace.sessions)

                        _uiState.value = _uiState.value.copy(
                            upcomingRace = mockRace,
                            nextSession = nextSession,
                            isLoading = false,
                            error = "Using demo race data: ${raceResult.exception.message}"
                        )
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Error: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    private fun getMockDriver(): Driver {
        return Driver(
            position = 1,
            firstName = "Lando",
            lastName = "Norris",
            teamName = "McLaren",
            points = 357,
            racingNumber = 4,
            driverCode = "NOR",
            driverId = "lando-norris",
            teamId = "mclaren",
            wins = 6,
            podiums = 16,
            poles = 5
        )
    }

    private fun getMockRace(): Race {
        val now = Instant.now()
        val futureDate = now.plusSeconds(7 * 24 * 60 * 60) // 7 days from now

        return Race(
            raceId = "mock-sao-paulo",
            circuitId = "sao_paulo",
            raceName = "São Paulo Grand Prix",
            raceStartTime = futureDate.epochSecond,
            raceEndTime = futureDate.plusSeconds(2 * 60 * 60).epochSecond,
            raceState = "upcoming",
            round = 21,
            isSprint = false,
            sessions = listOf(
                Session(
                    sessionId = "mock-fp1",
                    sessionType = "practice",
                    sessionName = "Practice 1",
                    startTime = futureDate.minusSeconds(48 * 60 * 60).epochSecond,
                    endTime = futureDate.minusSeconds(47 * 60 * 60).epochSecond,
                    sessionState = "upcoming"
                ),
                Session(
                    sessionId = "mock-quali",
                    sessionType = "quali",
                    sessionName = "Qualifying",
                    startTime = futureDate.minusSeconds(24 * 60 * 60).epochSecond,
                    endTime = futureDate.minusSeconds(23 * 60 * 60).epochSecond,
                    sessionState = "upcoming"
                ),
                Session(
                    sessionId = "mock-race",
                    sessionType = "race",
                    sessionName = "Race",
                    startTime = futureDate.epochSecond,
                    endTime = futureDate.plusSeconds(2 * 60 * 60).epochSecond,
                    sessionState = "upcoming"
                )
            ),
            podium = null
        )
    }
}