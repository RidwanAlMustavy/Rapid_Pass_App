package com.example.rapidpass

data class User(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val phone: String = "",
    val role: String = "user",
    val totalRecharge: Float = 0f
)
