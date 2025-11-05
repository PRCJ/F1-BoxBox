package com.boxbox.f1app.presentation.racedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.f1app.BoxBoxApplication
import com.boxbox.f1app.data.model.Race
import com.boxbox.f1app.data.remote.ApiService
import com.boxbox.f1app.data.repository.RaceRepository
import com.boxbox.f1app.domain.model.CircuitData
import com.boxbox.f1app.domain.model.CircuitDetail
import com.boxbox.f1app.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RaceDetailUiState(
    val race: Race? = null,
    val circuitDetail: CircuitDetail? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class RaceDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val raceId: String = checkNotNull(savedStateHandle["raceId"])

    private val apiService = ApiService(BoxBoxApplication.instance.ktorClient.client)
    private val raceRepository = RaceRepository(apiService)

    private val _uiState = MutableStateFlow(RaceDetailUiState())
    val uiState: StateFlow<RaceDetailUiState> = _uiState.asStateFlow()

    init {
        loadRaceDetails()
    }

    private fun loadRaceDetails() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            when (val result = raceRepository.getAllRaces()) {
                is Result.Success -> {
                    val race = result.data.firstOrNull { it.id == raceId }

                    if (race != null) {
                        val circuitDetail = CircuitData.getCircuitDetails(race.circuitName)

                        _uiState.value = _uiState.value.copy(
                            race = race,
                            circuitDetail = circuitDetail,
                            isLoading = false
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            error = "Race not found",
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to load race: ${result.exception.message}",
                        isLoading = false
                    )
                }
                else -> {}
            }
        }
    }
}