package com.example.orderfood.model

data class Login(
    val email: String,
    val password: String,
    val device_key: String? = null
)
