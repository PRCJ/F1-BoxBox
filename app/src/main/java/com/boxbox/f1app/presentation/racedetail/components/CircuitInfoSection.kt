package com.boxbox.f1app.presentation.racedetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.boxbox.f1app.domain.model.CircuitDetail

@Composable
fun CircuitInfoSection(circuitDetail: CircuitDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Circuit Information",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        InfoRow(label = "Location", value = "${circuitDetail.location}, ${circuitDetail.country}")
        InfoRow(label = "First Grand Prix", value = circuitDetail.firstGrandPrix)
        InfoRow(label = "Number of Laps", value = circuitDetail.numberOfLaps.toString())
        InfoRow(label = "Circuit Length", value = circuitDetail.circuitLength)
        InfoRow(label = "Race Distance", value = circuitDetail.raceDistance)

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "LAP RECORD",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
            Text(
                text = "${circuitDetail.lapRecord.time} - ${circuitDetail.lapRecord.driver} (${circuitDetail.lapRecord.year})",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}