package com.example.ar.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ar.ui.components.*
import com.example.ar.data.RetrofitInstance

@Composable
fun ProfileScreen(
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

    val customBlue = Color(0xFF1A0BE9)
    val activeColor = Color(0xFF1A0BE9)
    val inactiveColor = Color.Gray

    var nom by remember { mutableStateOf("user") }
    var prenom by remember { mutableStateOf("") }
    var emailOrPhone by remember { mutableStateOf("user@gmail.com") }

    LaunchedEffect(userId) {
        if (userId != null) {
            try {
                val response = RetrofitInstance.authApi.getUserById(userId)

                if (response.success) {
                    nom = response.nom ?: "user"
                    prenom = response.prenom ?: ""
                    emailOrPhone = response.emailOrPhone ?: ""
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
                .padding(bottom = 80.dp)
        ) {

            Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                TopBar(
                    onMenuClick = { isMenuOpen = !isMenuOpen },
                    onLogoClick = { onNavigateHome() }
                )



                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(50.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("$prenom $nom", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(emailOrPhone, color = GrayText, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileStat("5", "Projets")
                    ProfileStat("2", "Favoris")
                    ProfileStat("3", "AR scans")
                }

                Spacer(modifier = Modifier.height(30.dp))

                ProfileActionItem("Mes Projets") { onNavigateToProjects() }
                ProfileActionItem("Catalogue") { onNavigateToCatalogue() }

                Spacer(modifier = Modifier.height(120.dp))

                SimpleLogoutButton(onClick = onLogout)
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

                Text("SmartHome", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = customBlue)

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
                    modifier = Modifier.fillMaxWidth().clickable {
                        isMenuOpen = false
                        onNavigateToCatalogue()
                    }.padding(12.dp)
                )

                Text(
                    "Mes Projets",
                    color = if (currentScreen == "projects") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth().clickable {
                        isMenuOpen = false
                        onNavigateToProjects()
                    }.padding(12.dp)
                )

                Text(
                    "Profil",
                    color = if (currentScreen == "profile") activeColor else inactiveColor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.fillMaxWidth().clickable {
                        isMenuOpen = false
                        onNavigateToProfile()
                    }.padding(12.dp)
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
            currentRoute = "profile",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(0.9f)
        )
    }
}


@Composable
fun ProfileStat(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(number, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(label, color = GrayText, fontSize = 12.sp)
    }
}


@Composable
fun ProfileActionItem(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(title, fontWeight = FontWeight.Medium)
    }
}