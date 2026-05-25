package com.example.ar.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthApi {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("auth/user/{id}")
    suspend fun getUserById(@Path("id") id: Long): AuthResponse

    @GET("projects/user/{userId}")
    suspend fun getProjectsByUser(
        @Path("userId") userId: Long
    ): List<Project>
}