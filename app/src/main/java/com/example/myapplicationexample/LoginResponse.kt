package com.example.myapplicationexample

data class LoginResponse(
    val status: String,
    val lecturerId: Int?,
    val name: String?,
    val email: String?,
    val reason: String? // if login fails
)
