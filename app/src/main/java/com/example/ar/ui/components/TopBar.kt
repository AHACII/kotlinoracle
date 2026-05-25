package com.example.ar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    currentScreen: String = "",
    showCancel: Boolean = false,
    onCancelClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
    onLogoClick: () -> Unit = {}
) {

    val customBlue = Color(0xFF1A0BE9)

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                "SmartHome",
                color = customBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.clickable {
                    onLogoClick()
                }
            )

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                tint = customBlue,
                modifier = Modifier.clickable { onMenuClick() }
            )
        }
    }
}