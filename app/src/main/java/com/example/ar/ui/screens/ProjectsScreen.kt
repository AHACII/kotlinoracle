package com.example.ar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ar.R
import com.example.ar.data.Project
import com.example.ar.data.RetrofitInstance
import com.example.ar.ui.components.*

@Composable
fun ProjectsScreen(
    navController: NavController,
    userId: Long?,
    currentScreen: String,
    onNavigateHome: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToProjects: () -> Unit,
    onNavigateToCatalogue: () -> Unit,
    onLogout: () -> Unit
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    var projects by remember { mutableStateOf<List<Project>>(emptyList()) }

    val defaultProjects = listOf(
        Project(1, "Salon Moderne", "Design réalisé le 12/05/2024", userId ?: 0),
        Project(2, "Chambre Classique", "Design réalisé le 10/05/2024", userId ?: 0),
        Project(3, "Bureau Pro", "Design réalisé le 05/05/2024", userId ?: 0)
    )

    LaunchedEffect(userId) {
        if (userId != null) {
            try {
                val apiProjects = RetrofitInstance.projectApi.getProjectsByUser(userId)
                projects = if (apiProjects.isEmpty()) defaultProjects else apiProjects
            } catch (e: Exception) {
                projects = defaultProjects
                e.printStackTrace()
            }
        } else {
            projects = defaultProjects
        }
    }

    val customBlue = Color(0xFF1A0BE9)
    val activeColor = Color(0xFF1A0BE9)
    val inactiveColor = Color.Gray

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            TopBar(
                onMenuClick = { isMenuOpen = !isMenuOpen },
                onLogoClick = { onNavigateHome() }
            )

            Text(
                text = "Mes Projets AR",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp)
            ) {

                item {
                    Text(
                        text = "Retrouvez ici vos designs sauvegardés",
                        fontSize = 14.sp,
                        color = GrayText,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                }

                items(projects) { project ->

                    val imageRes = when (project.name) {
                        "Salon Moderne" -> R.drawable.salon
                        "Chambre classique" -> R.drawable.chambre
                        "Bureau" -> R.drawable.bureau
                        else -> R.drawable.salon
                    }

                    ProjectCard(
                        title = project.name,
                        date = project.description,
                        imageRes = imageRes
                    )
                }
            }
        }

        if (isMenuOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { isMenuOpen = false }
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(220.dp)
                    .background(Color.White)
                    .padding(20.dp)
            ) {

                Text(
                    "SmartHome",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = customBlue
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    "Accueil",
                    color = if (currentScreen == "home") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isMenuOpen = false
                            onNavigateHome()
                        }
                        .padding(12.dp)
                )

                Text(
                    "Catalogue",
                    color = if (currentScreen == "catalogue") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isMenuOpen = false
                            onNavigateToCatalogue()
                        }
                        .padding(12.dp)
                )

                Text(
                    "Mes Projets",
                    color = if (currentScreen == "projects") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isMenuOpen = false
                            onNavigateToProjects()
                        }
                        .padding(12.dp)
                )

                Text(
                    "Profil",
                    color = if (currentScreen == "profile") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isMenuOpen = false
                            onNavigateToProfile()
                        }
                        .padding(12.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    "Se déconnecter",
                    color = Color.Red,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            isMenuOpen = false
                            onLogout()
                        }
                        .padding(12.dp)
                )
            }
        }

        BottomMenu(
            navController = navController,
            currentRoute = "projects",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f)
        )
    }
}