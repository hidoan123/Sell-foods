package com.example.orderfood.model

import java.io.Serializable

data class NhanXet(
    val id_user : Int,
    val id_product:Int,
    val noi_dung:String,
    val ngay_nhan_xet : String
):Serializable



data class NhanXetItem(
    val user_name : String,
    val noi_dung : String,
    val ngay_nhan_xet : String
)