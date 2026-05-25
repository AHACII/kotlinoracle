package com.example.ar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun ExploreCard(title: String) {

    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color(0xFF6C63FF), RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(title, color = Color.White)
    }
}