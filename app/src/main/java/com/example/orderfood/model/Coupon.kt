package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class Coupon(

	@field:SerializedName("code")
	val code: Any? = null,

	@field:SerializedName("trangthai")
	val trangthai: Int? = null,

	@field:SerializedName("giamgia")
	val giamgia: Int? = null,

	@field:SerializedName("loaigiam")
	val loaigiam: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("hinhanh")
	val hinhanh: String? = null,

	@field:SerializedName("ngaykt")
	val ngaykt: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("ten")
	val ten: String? = null,

	@field:SerializedName("mota")
	val mota: String? = null,

	@field:SerializedName("dieukien")
	val dieukien: String? = null,

	@field:SerializedName("hienthi")
	val hienthi: Int? = null,

	@field:SerializedName("ngaybd")
	val ngaybd: String? = null
)
