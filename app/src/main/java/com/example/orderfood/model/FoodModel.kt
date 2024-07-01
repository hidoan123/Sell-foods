package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class FoodModel(

	@field:SerializedName("danhmuc")
	val danhmuc: Danhmuc? = null,

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
	val slug: String? = null
)

data class Danhmuc(

	@field:SerializedName("hinhanh")
	val hinhanh: String? = null,

	@field:SerializedName("trangthai")
	val trangthai: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("tenloai")
	val tenloai: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("mota")
	val mota: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
)
