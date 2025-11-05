package com.boxbox.f1app.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boxbox.f1app.R
import com.boxbox.f1app.data.model.Driver
import com.boxbox.f1app.presentation.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun HeroSlider(
    drivers: List<Driver>,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val sliderHeight = screenHeight * 0.5f // 50% of screen height

    // Pager with 2 slides: Driver card + Community card
    val pagerState = rememberPagerState(pageCount = { 2 })

    // Auto-scroll every 3 seconds
    LaunchedEffect(pagerState) {
        while (isActive) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % 2
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(sliderHeight)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> DriverSlide(driver = drivers.firstOrNull())
                1 -> CommunitySlide()
            }
        }

        // Page indicators at the bottom
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(2) { index ->
                Box(
                    modifier = Modifier
                        .width(if (pagerState.currentPage == index) 24.dp else 8.dp)
                        .height(8.dp)
                        .background(
                            color = if (pagerState.currentPage == index) {
                                Color.White
                            } else {
                                Color.White.copy(alpha = 0.3f)
                            },
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun DriverSlide(driver: Driver?) {
    if (driver == null) return

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFF5A08),
                            Color(0xFFFF5A08).copy(alpha = 0.9f)
                        )
                    )
                )
        ) {
            // Large "Lando" text background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 24.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Lando",
                    fontSize = 120.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFFF7A3A).copy(alpha = 0.3f),
                    lineHeight = 120.sp
                )
            }

            // Driver image - positioned from top-left with exact specs
            Image(
                painter = painterResource(id = R.drawable.racer_image),
                contentDescription = "Driver",
                modifier = Modifier
                    .size(387.dp)
                    .offset(x = 72.dp, y = 70.dp),
                contentScale = ContentScale.Fit
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Top row: Get Pro button with diamond icon (no color filter)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Get Pro button - transparent white filled
                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.25f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(36.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.diamond),
                            contentDescription = "Diamond",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Unspecified // No color filter - use PNG as is
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Get Pro",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Stats row with icons - POS and WINS
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    // Position stat
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.alt),
                            contentDescription = "Position",
                            modifier = Modifier.size(16.dp),
                            tint = Color.White
                        )
                        Text(
                            text = "0${driver.position}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Pos",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    // Wins stat
                    driver.wins?.let { wins ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.wins_icon),
                                contentDescription = "Wins",
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFFFFD700)
                            )
                            Text(
                                text = String.format("%02d", wins),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "Wins",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                // Points with PTS badge
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Points number with gradient
                    Text(
                        text = driver.points.toString(),
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        lineHeight = 70.sp,
                        style = androidx.compose.ui.text.TextStyle(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White,
                                    Color(0xFFFF5A08)
                                )
                            )
                        )
                    )

                    // PTS badge
                    Surface(
                        color = Color(0xFFFF5A08),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Text(
                            text = "PTS",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CommunitySlide() {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = F1Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1A1A),
                            Color(0xFF0A0A0A)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Top row: Get Pro button with diamond icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Get Pro button - transparent white filled
                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.25f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(36.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.diamond),
                            contentDescription = "Diamond",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Unspecified // No color filter - use PNG as is
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Get Pro",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(86.dp)) // 110 - 24 (top padding)

                // Box container for green background and image
                Box(
                    modifier = Modifier
                        .width(271.dp)
                        .height(214.dp)
                        .padding(start = 28.dp)
                ) {
                    // Green box behind "AN APP" text
                    Box(
                        modifier = Modifier
                            .offset(x = 112.dp, y = 101.dp) // Adjusted for positioning
                            .width(145.dp)
                            .height(57.dp)
                            .graphicsLayer {
                                rotationZ = 6.6f // Rotate 6.6 degrees
                            }
                            .background(
                                color = Color(0xFF86FF0E),
                                shape = RoundedCornerShape(10.dp)
                            )
                    )

                    // Community image with exact dimensions
                    Image(
                        painter = painterResource(id = R.drawable.text),
                        contentDescription = "We are more than just an app",
                        modifier = Modifier
                            .width(271.dp)
                            .height(214.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Follow Us button - centered
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { /* Open Instagram */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrightGreen,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .height(48.dp)
                            .width(140.dp)
                    ) {
                        Text(
                            "Follow Us",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}