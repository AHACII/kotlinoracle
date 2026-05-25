package com.example.ar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.ar.ui.screens.*
import com.example.ar.ui.components.LightBg

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = LightBg) {
                    val navController = rememberNavController()
                    var userPhone by remember { mutableStateOf("") }
                    var connectedUserId by remember { mutableStateOf<Long?>(null) }

                    NavHost(navController = navController, startDestination = "signin") {
                        composable("signin") {
                            SignInScreen(
                                onGoToSignUp = { navController.navigate("signup") },
                                onLoginSuccess = { userId, role ->
                                    connectedUserId = userId
                                    if (role == "admin") {
                                        navController.navigate("admin")
                                    } else {
                                        navController.navigate("home")
                                    }
                                }
                            )
                        }
                        composable("admin") {
                            AdminPanelScreen(
                                navController = navController,
                                onLogout = {
                                    connectedUserId = null
                                    navController.navigate("signin")
                                }
                            )
                        }
                        composable("signup") {
                            SignUpScreen(
                                onRegisterSuccess = { phone -> 
                                    userPhone = phone
                                    navController.navigate("verification") 
                                },
                                onBackToSignIn = { navController.popBackStack() }
                            )
                        }
                        composable("verification") {
                            VerificationScreen(
                                phoneNumber = userPhone,
                                onVerifySuccess = { navController.navigate("home") },
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("home") {
                            SmartHomeScreen(
                                navController = navController,
                                currentScreen = "home",
                                onNavigateToScanner = { navController.navigate("scanner") },
                                onNavigateHome = { },
                                onNavigateToProfile = { navController.navigate("profile") },
                                onNavigateToProjects = { navController.navigate("projects") },
                                onNavigateToCatalogue = { category -> 
                                    if (category != null) {
                                        navController.navigate("catalogue?category=$category")
                                    } else {
                                        navController.navigate("catalogue")
                                    }
                                },
                                onLogout = { navController.navigate("signin") },
                            )
                        }
                        composable(
                            route = "catalogue?category={category}",
                            arguments = listOf(navArgument("category") { 
                                defaultValue = "Touts"
                                nullable = true 
                            })
                        ) { backStackEntry ->
                            val category = backStackEntry.arguments?.getString("category") ?: "Touts"
                            CatalogueScreen(
                                navController = navController,
                                currentScreen = "catalogue",
                                initialCategory = category,
                                onNavigateHome = { navController.navigate("home") },
                                onNavigateToProfile = { navController.navigate("profile") },
                                onNavigateToProjects = { navController.navigate("projects") },
                                onNavigateToCatalogue = { },
                                onLogout = { navController.navigate("signin") },
                                onNavigateToAR = { navController.navigate("ar") },
                            )
                        }
                        composable("projects") {
                            ProjectsScreen(
                                navController = navController,
                                userId = connectedUserId,
                                currentScreen = "projects",
                                onNavigateHome = { navController.navigate("home") },
                                onNavigateToProfile = { navController.navigate("profile") },
                                onNavigateToProjects = { },
                                onNavigateToCatalogue = { navController.navigate("catalogue") },
                                onLogout = {
                                    connectedUserId = null
                                    navController.navigate("signin")
                                },
                            )
                        }
                        composable("profile") {
                            ProfileScreen(
                                navController = navController,
                                userId = connectedUserId,
                                currentScreen = "profile",
                                onNavigateHome = { navController.navigate("home") },
                                onNavigateToProfile = { },
                                onNavigateToProjects = { navController.navigate("projects") },
                                onNavigateToCatalogue = { navController.navigate("catalogue") },
                                onLogout = {
                                    connectedUserId = null
                                    navController.navigate("signin")
                                },
                            )
                        }
                        composable("scanner") {
                            ScannerARScreen(
                                navController = navController,
                                onNavigateToAR = { navController.navigate("ar") },
                                onNavigateHome = { navController.navigate("home") },
                                onNavigateToProfile = { navController.navigate("profile") },
                                onNavigateToProjects = { navController.navigate("projects") },
                                onNavigateToCatalogue = { navController.navigate("catalogue") },
                            )
                        }
                        composable("ar") {
                            ARPlacementScreen(
                                navController = navController,
                                onNavigateHome = { navController.navigate("home") }
                            )
                        }
                    }
                }
            }
        }
    }
}
