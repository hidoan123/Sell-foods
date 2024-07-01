package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class DetailFood(

    @field:SerializedName("id_loaisanpham")
    val idLoaisanpham: Int? = null,

    @field:SerializedName("hinhanh")
    val hinhanh: String? = null,

    @field:SerializedName("trangthai")
    val trangthai: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("tensp")
    val tensp: String? = null,

    @field:SerializedName("giaban")
    val giaban: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("mota")
    val mota: String? = null,

    @field:SerializedName("noidung")
    val noidung: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("reviews")
    val reviews: ArrayList<Review>
) {
    data class Review(
        val id: Int,
        val id_sanpham: Int,
        val danhgia: Int
    )
}
