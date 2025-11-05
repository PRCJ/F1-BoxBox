package com.boxbox.f1app.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.boxbox.f1app.R
import com.boxbox.f1app.presentation.theme.F1Black

@Composable
fun BottomNavBar(
    selectedIndex: Int = 0,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        containerColor = F1Black,
        contentColor = Color.White
    ) {
        // Home
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = { onItemSelected(0) },
            icon = {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = if (selectedIndex == 0) Color.White else Color.Transparent,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.nav_home),
                        contentDescription = "Home",
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedIndex == 0) F1Black else Color.Gray
                    )
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = F1Black,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )

        // Calendar
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = { onItemSelected(1) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.nav_calendar),
                    contentDescription = "Calendar",
                    modifier = Modifier.size(24.dp),
                    tint = if (selectedIndex == 1) Color.White else Color.Gray
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )

        // Trophy
        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = { onItemSelected(2) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.nav_trophy),
                    contentDescription = "Trophy",
                    modifier = Modifier.size(24.dp),
                    tint = if (selectedIndex == 2) Color.White else Color.Gray
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )

        // Globe
        NavigationBarItem(
            selected = selectedIndex == 3,
            onClick = { onItemSelected(3) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.nav_globus),
                    contentDescription = "Globe",
                    modifier = Modifier.size(24.dp),
                    tint = if (selectedIndex == 3) Color.White else Color.Gray
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )

        // Profile
        NavigationBarItem(
            selected = selectedIndex == 4,
            onClick = { onItemSelected(4) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(24.dp),
                    tint = if (selectedIndex == 4) Color.White else Color.Gray
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
    }
}