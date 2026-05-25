package com.example.ar.data

import retrofit2.http.GET
import retrofit2.http.Path

interface ProjectApi {

    @GET("projects/user/{userId}")
    suspend fun getProjectsByUser(
        @Path("userId") userId: Long
    ): List<Project>
}