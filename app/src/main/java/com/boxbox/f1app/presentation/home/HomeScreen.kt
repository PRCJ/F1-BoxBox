package com.boxbox.f1app.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.boxbox.f1app.presentation.home.components.HeroSlider
import com.boxbox.f1app.presentation.home.components.InstagramCard
import com.boxbox.f1app.presentation.home.components.SessionCardsSection

@Composable
fun HomeScreen(
    onNavigateToRaceDetail: (String) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

            uiState.error != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Error: ${uiState.error}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { viewModel.loadData() }) {
                        Text("Retry")
                    }
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Hero slider
                    if (uiState.topDrivers.isNotEmpty()) {
                        HeroSlider(drivers = uiState.topDrivers)
                    }

                    // In HomeScreen, update SessionCardsSection call:
                    if (uiState.upcomingRace != null && uiState.nextSession != null) {
                        SessionCardsSection(
                            race = uiState.upcomingRace!!,
                            nextSession = uiState.nextSession!!,
                            onGreenCardClick = {
                                onNavigateToRaceDetail(uiState.upcomingRace!!.id)
                            }
                        )
                    }
                    // Instagram card (replaced upcoming race card)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))

                        InstagramCard()

                        Spacer(modifier = Modifier.height(80.dp)) // Space for bottom nav
                    }
                }
            }
        }
    }
}