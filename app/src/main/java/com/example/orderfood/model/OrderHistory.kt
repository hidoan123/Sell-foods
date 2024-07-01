package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class OrderHistory(

	@field:SerializedName("tongtien")
	val tongtien: Int? = null,

	@field:SerializedName("trangthaithanhtoan")
	val trangthaithanhtoan: Int? = null,

	@field:SerializedName("dienthoai")
	val dienthoai: String? = null,

	@field:SerializedName("trangthai")
	val trangthai: Int? = null,

	@field:SerializedName("ghichu")
	val ghichu: String? = null,

	@field:SerializedName("id_khachhang")
	val idKhachhang: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("madh")
	val madh: String? = null,

	@field:SerializedName("id_nhanvien")
	val idNhanvien: Any? = null,

	@field:SerializedName("tongdonhang")
	val tongdonhang: Int? = null,

	@field:SerializedName("diachi")
	val diachi: String? = null,

	@field:SerializedName("ngaytao")
	val ngaytao: String? = null,

	@field:SerializedName("id_coupon")
	val idCoupon: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_feeship")
	val idFeeship: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("hoten")
	val hoten: String? = null,

	@field:SerializedName("httt")
	val httt: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
