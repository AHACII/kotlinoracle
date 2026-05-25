package com.example.ar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController // ضروري تزيد هاد الـ Import
import com.example.ar.R

@Composable
fun BottomMenu(
    navController: NavController,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    val accentColor = Color(0xFF3D5AFE)
    val backgroundColor = Color(0xFFF5F7FA)

    Box(
        modifier = modifier
            .padding(bottom = 12.dp, start = 16.dp, end = 16.dp)
            .shadow(15.dp, RoundedCornerShape(50.dp))
            .clip(RoundedCornerShape(50.dp))
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            BottomItem(
                label = "Accueil",
                icon = R.drawable.ic_home,
                isSelected = currentRoute == "home"
            ) {
                if (currentRoute != "home") navController.navigate("home")
            }

            BottomItem(
                label = "Catalogue",
                icon = R.drawable.ic_catalogue,
                isSelected = currentRoute == "catalogue"
            ) {
                if (currentRoute != "catalogue") navController.navigate("catalogue")
            }

            BottomItem(
                label = "Profil",
                icon = R.drawable.ic_user,
                isSelected = currentRoute == "profile"
            ) {
                if (currentRoute != "profile") navController.navigate("profile")
            }

            BottomItem(
                label = "Projets",
                icon = R.drawable.ic_image,
                isSelected = currentRoute == "projects"
            ) {
                if (currentRoute != "projects") navController.navigate("projects")
            }
        }
    }
}

@Composable
fun BottomItem(label: String, icon: Int, isSelected: Boolean, onClick: () -> Unit) {
    val activeColor = Color(0xFF3D5AFE)
    val inactiveColor = Color(0xFF717171)
    val color = if (isSelected) activeColor else inactiveColor

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(5.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = color,
            style = MaterialTheme.typography.labelSmall
        )
    }
}