package com.example.orderfood.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Recipe (
    @field: SerializedName("id")
    val id : Int ?= null,
    @field: SerializedName("ten_mon")
    val ten_mon : String ?= null,
    @field: SerializedName("nguyen_lieu")
    val nguyen_lieu : String ?= null,
    @field: SerializedName("cach_nau")
    val cach_nau : String ?= null,
    @field: SerializedName("anh")
    val anh : String ?= null
) : Serializable