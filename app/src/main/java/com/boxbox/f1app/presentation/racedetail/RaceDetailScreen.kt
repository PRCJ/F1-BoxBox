package com.boxbox.f1app.presentation.racedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boxbox.f1app.R
import com.boxbox.f1app.data.model.Race
import com.boxbox.f1app.presentation.theme.BrightGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RaceDetailScreen(
    raceId: String,
    onNavigateBack: () -> Unit,
    viewModel: RaceDetailViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = BrightGreen)
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${uiState.error}",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        uiState.race != null -> {
            RaceDetailContent(
                race = uiState.race!!,
                onNavigateBack = onNavigateBack
            )
        }
    }
}

@Composable
private fun RaceDetailContent(
    race: Race,
    onNavigateBack: () -> Unit
) {
    // Format dates from timestamps
    val dateFormatter = SimpleDateFormat("dd - dd MMM", Locale.getDefault())
    val startDate = try {
        SimpleDateFormat("dd", Locale.getDefault()).format(Date(race.raceStartTime))
    } catch (e: Exception) {
        "23"
    }
    val endDate = try {
        SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(race.raceEndTime))
    } catch (e: Exception) {
        "30 April"
    }

    // Get first session for countdown
    val firstSession = race.sessions.firstOrNull()

    // Countdown timer state
    var daysLeft by remember { mutableIntStateOf(7) }
    var hoursLeft by remember { mutableIntStateOf(16) }
    var minutesLeft by remember { mutableIntStateOf(42) }

    // Calculate countdown
    LaunchedEffect(firstSession) {
        while (isActive) {
            try {
                if (firstSession != null) {
                    val now = System.currentTimeMillis()
                    val sessionTime = firstSession.startTime.toLong()
                    val diff = sessionTime - now

                    if (diff > 0) {
                        daysLeft = (diff / (1000 * 60 * 60 * 24)).toInt()
                        hoursLeft = ((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)).toInt()
                        minutesLeft = ((diff % (1000 * 60 * 60)) / (1000 * 60)).toInt()
                    } else {
                        daysLeft = 0
                        hoursLeft = 0
                        minutesLeft = 0
                    }
                }
            } catch (e: Exception) {
                // Keep default values
            }
            delay(60000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF006644), // Dark green at top
                        Color.Black // Black at bottom
                    ),
                    startY = 0f,
                    endY = 800f
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        // Title at top center
        Text(
            text = "Upcoming race",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 24.dp)
        )

        // Hero Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Round number
                Text(
                    text = "Round ${race.round}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Race name
                Text(
                    text = race.raceName,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 36.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Date range
                Text(
                    text = "$startDate - $endDate",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            // Circuit image overlay
            Image(
                painter = painterResource(id = R.drawable.track),
                contentDescription = "Circuit",
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 10.dp),
                contentScale = ContentScale.Fit,
                alpha = 0.5f
            )
        }

        // Countdown Timer Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = "FP1 Starts in",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CountdownItem(
                    value = String.format("%02d", daysLeft),
                    label = "Days"
                )
                CountdownItem(
                    value = String.format("%02d", hoursLeft),
                    label = "Hours"
                )
                CountdownItem(
                    value = String.format("%02d", minutesLeft),
                    label = "Minutes"
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Circuit Description Section - Black background
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(24.dp)
        ) {
            // Circuit name
            Text(
                text = "${race.circuitName} Circuit",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Circuit description
            Text(
                text = "Bahrain International circuit is located in Sakhir, Bahrain and it was designed by German architect Hermann Tilke. It was built on the site of a former camel farm, in Sakhir. It measures 5.412 km, has 15 corners and 3 DRS Zones. The Grand Prix has 57 laps. This circuit has 6 alternative layouts.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Circuit Facts
            Text(
                text = "Circuit Facts",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Facts
            val facts = listOf(
                "His brother Arthur Leclerc is currently set to race for DAMS in the 2023 F2 Championship",
                "He's not related to Edouard Leclerc, the founder of a French supermarket chain"
            )

            facts.forEach { fact ->
                FactItem(fact = fact)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun CountdownItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00884D), // Darker shade of green
            lineHeight = 40.sp
        )
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun FactItem(fact: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .offset(y = 7.dp)
                .background(
                    color = BrightGreen,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(3.dp)
                )
        )

        Text(
            text = fact,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.White.copy(alpha = 0.9f),
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}