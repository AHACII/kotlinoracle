package com.example.ar.data

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<ApiProduct>

    @POST("products")
    suspend fun addProduct(@Body product: ApiProduct): ApiProduct

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Long)
}