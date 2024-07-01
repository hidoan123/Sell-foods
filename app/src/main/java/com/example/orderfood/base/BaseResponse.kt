package com.example.orderfood.base

data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val Result: String,
    val data: T
)