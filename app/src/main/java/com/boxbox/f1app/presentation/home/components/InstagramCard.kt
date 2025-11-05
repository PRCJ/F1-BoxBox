package com.boxbox.f1app.presentation.home.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.boxbox.f1app.R

@Composable
fun InstagramCard(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val instagramUrl = "https://www.instagram.com/boxbox_club/"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Instagram poster as background
            Image(
                painter = painterResource(id = R.drawable.poster),
                contentDescription = "Instagram Post",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Instagram icon in bottom-right corner
            Icon(
                painter = painterResource(id = R.drawable.ic_insta),
                contentDescription = "Instagram",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                tint = Color.White
            )
        }
    }
}