package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class AddOrderDetail(

	@field:SerializedName("id_donhang")
	val idDonhang: String? = null,

	@field:SerializedName("giagoc")
	val giagoc: String? = null,

	@field:SerializedName("id_size")
	val idSize: String? = null,

	@field:SerializedName("giaban")
	val giaban: String? = null,

	@field:SerializedName("soluong")
	val soluong: String? = null,

	@field:SerializedName("id_sanpham")
	val idSanpham: String? = null
)
