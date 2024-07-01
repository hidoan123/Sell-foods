package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class Category(

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
