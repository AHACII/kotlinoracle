package com.example.ar.data

data class RegisterRequest(
    val nom: String,
    val prenom: String,
    val dateNaissance: String,
    val emailOrPhone: String,
    val password: String
)

data class LoginRequest(
    val emailOrPhone: String,
    val password: String
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val userId: Long?,
    val nom: String?,
    val prenom: String?,
    val emailOrPhone: String?,
    val role: String? = "user"
)