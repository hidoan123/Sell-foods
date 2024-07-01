package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class AddOrder(

	@field:SerializedName("Data")
	val data: Data? = null,

	@field:SerializedName("Result")
	val result: String? = null
)

data class Data(

	@field:SerializedName("tongtien")
	val tongtien: String? = null,

	@field:SerializedName("trangthaithanhtoan")
	val trangthaithanhtoan: String? = null,

	@field:SerializedName("dienthoai")
	val dienthoai: String? = null,

	@field:SerializedName("trangthai")
	val trangthai: String? = null,

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
	val tongdonhang: String? = null,

	@field:SerializedName("diachi")
	val diachi: String? = null,

	@field:SerializedName("ngaytao")
	val ngaytao: String? = null,

	@field:SerializedName("id_coupon")
	val idCoupon: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_feeship")
	val idFeeship: String? = null,

	@field:SerializedName("hoten")
	val hoten: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("httt")
	val httt: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
