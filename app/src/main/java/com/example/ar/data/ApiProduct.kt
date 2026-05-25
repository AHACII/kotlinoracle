package com.example.ar.data

data class ApiProduct(
    val id: Long,
    val name: String,
    val price: Double,
    val image: String?,
    val category: String = "Salon"
)