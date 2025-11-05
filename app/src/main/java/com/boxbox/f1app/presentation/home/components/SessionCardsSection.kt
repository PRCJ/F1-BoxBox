package com.boxbox.f1app.presentation.home.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boxbox.f1app.R
import com.boxbox.f1app.data.model.Race
import com.boxbox.f1app.data.model.Session
import com.boxbox.f1app.presentation.theme.*
import com.boxbox.f1app.util.DateTimeUtil

@Composable
fun SessionCardsSection(
    race: Race,
    nextSession: Session,
    onGreenCardClick: () -> Unit, // Add this parameter
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Large green session card (left side) - Now clickable
        GreenSessionCard(
            nextSession = nextSession,
            onClick = onGreenCardClick, // Pass click handler
            modifier = Modifier.weight(1f)
        )

        // Two smaller cards stacked (right side)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RedMediumCard()
            BlueEducationCard()
        }
    }
}

@Composable
private fun GreenSessionCard(
    nextSession: Session,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(132.dp)
            .clickable { onClick() }, // Add clickable
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF044331)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Track icon in top-right corner
            Icon(
                painter = painterResource(id = R.drawable.ic_track),
                contentDescription = "Track",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 12.dp, end = 12.dp),
                tint = Color.White
            )

            // Content column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
            ) {
                // Session name from API
                Text(
                    text = nextSession.sessionName.take(3).uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Date with calendar icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_check),
                        contentDescription = "Calendar",
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                    Text(
                        text = DateTimeUtil.formatToDate(nextSession.startTime).take(12),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Time
                Text(
                    text = DateTimeUtil.formatToTime(nextSession.startTime) + "AM",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF02BB81),
                    lineHeight = 36.sp
                )
            }
        }
    }
}
@Composable
private fun RedMediumCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFF0000),
                            Color(0xFF000000)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Medium icon
                Icon(
                    painter = painterResource(id = R.drawable.km),
                    contentDescription = "Medium",
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )

                // Distance text
                Text(
                    text = "7015.3km",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun BlueEducationCard() {
    val context = LocalContext.current
    val url = "https://blog.boxbox.club/tagged/beginners-guide"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(BlueStart, BlueEnd)
                    )
                )
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Play icons on left
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.medium),
                        contentDescription = "Medium",
                        modifier = Modifier.size(32.dp),
                        tint = Color.White
                    )

                }

                // Text in center
                Text(
                    text = "Formula 1\nEducation",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 15.sp
                )

                // Arrow icon on right
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "Arrow",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
    }
}