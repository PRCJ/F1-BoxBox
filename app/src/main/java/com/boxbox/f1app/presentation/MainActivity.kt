package com.boxbox.f1app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.boxbox.f1app.presentation.navigation.BottomNavBar
import com.boxbox.f1app.presentation.navigation.NavGraph
import com.boxbox.f1app.presentation.theme.F1BoxBoxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            F1BoxBoxTheme {
                var selectedNavIndex by remember { mutableStateOf(0) }

                Scaffold(
                    bottomBar = {
                        BottomNavBar(
                            selectedIndex = selectedNavIndex,
                            onItemSelected = { index ->
                                selectedNavIndex = index
                            }
                        )
                    }
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}