package com.example.ar.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.ar.data.LoginRequest
import com.example.ar.data.RegisterRequest
import com.example.ar.data.RetrofitInstance
import com.example.ar.ui.components.*
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    onGoToSignUp: () -> Unit,
    onLoginSuccess: (Long, String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppHeader(title = "Connectez")
        Spacer(modifier = Modifier.height(40.dp))

        CustomCard {
            CustomInput(
                label = "Email ou numéro du téléphone",
                value = email,
                onValueChange = { email = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomInput(
                label = "Mot de passe",
                value = pass,
                onValueChange = { pass = it },
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.contains("réussie", ignoreCase = true)) Color(0xFF1A8F3A) else Color.Red,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                PrimaryButton(
                    text = "Continue",
                    onClick = {
                        scope.launch {
                            isLoading = true
                            message = ""

                            try {
                                val response = RetrofitInstance.authApi.login(
                                    LoginRequest(
                                        emailOrPhone = email,
                                        password = pass
                                    )
                                )

                                message = response.message

                                if (response.success) {
                                    val userId = response.userId
                                    val role = response.role ?: "user"
                                    if (userId != null) {
                                        onLoginSuccess(userId, role)
                                    }
                                }

                            } catch (e: Exception) {
                                message = "Le backend n'est pas accessible. Veuillez vérifier votre connexion."
                                e.printStackTrace()
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            NavigationLink(
                text1 = "vous n'avez pas de compte? ",
                text2 = "Inscrivez_Vous",
                onClick = onGoToSignUp
            )
        }
    }
}

@Composable
fun SignUpScreen(
    onRegisterSuccess: (String) -> Unit,
    onBackToSignIn: () -> Unit
) {
    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppHeader(title = "Inscrivez")
        Spacer(modifier = Modifier.height(40.dp))

        CustomCard {
            CustomInput(
                label = "Nom",
                value = nom,
                onValueChange = { nom = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomInput(
                label = "Prénom",
                value = prenom,
                onValueChange = { prenom = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomInput(
                label = "Date de naissance",
                value = date,
                onValueChange = { date = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomInput(
                label = "Email ou numéro du téléphone",
                value = emailOrPhone,
                onValueChange = { emailOrPhone = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomInput(
                label = "Mot de passe",
                value = password,
                onValueChange = { password = it },
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    color = if (message.contains("réussie", ignoreCase = true)) Color(0xFF1A8F3A) else Color.Red,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                PrimaryButton(
                    text = "Continuer",
                    onClick = {
                        scope.launch {
                            isLoading = true
                            message = ""

                            try {
                                val response = RetrofitInstance.authApi.register(
                                    RegisterRequest(
                                        nom = nom,
                                        prenom = prenom,
                                        dateNaissance = date,
                                        emailOrPhone = emailOrPhone,
                                        password = password
                                    )
                                )

                                message = response.message

                                if (response.success) {
                                    onRegisterSuccess(emailOrPhone)
                                }

                            } catch (e: Exception) {
                                message = "Erreur de connexion au serveur"
                                e.printStackTrace()
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            NavigationLink(
                text1 = "Vous avez déjà un compte ? ",
                text2 = "Se connecter",
                onClick = onBackToSignIn
            )
        }
    }
}

@Composable
fun VerificationScreen(
    phoneNumber: String,
    onVerifySuccess: () -> Unit,
    onBack: () -> Unit
) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppHeader(title = "Verify")
        Spacer(modifier = Modifier.height(40.dp))

        CustomCard {
            Text(
                text = "Enter the code sent to you at $phoneNumber",
                color = MainBlack,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomInput(
                label = "Code",
                value = code,
                onValueChange = { code = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Resend code",
                color = GrayText,
                fontSize = 13.sp,
                modifier = Modifier.clickable { }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Back",
                color = GrayText,
                fontSize = 13.sp,
                modifier = Modifier.clickable { onBack() }
            )

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryButton(
                text = "Verify Now",
                onClick = onVerifySuccess
            )
        }
    }
}