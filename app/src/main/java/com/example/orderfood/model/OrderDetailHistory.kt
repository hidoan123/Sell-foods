package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class OrderDetailHistory(

	@field:SerializedName("tongtien")
	val tongtien: Int? = null,

	@field:SerializedName("trangthaithanhtoan")
	val trangthaithanhtoan: Int? = null,

	@field:SerializedName("dienthoai")
	val dienthoai: String? = null,

	@field:SerializedName("trangthai")
	val trangthai: Int? = null,

	@field:SerializedName("ghichu")
	val ghichu: Any? = null,

	@field:SerializedName("id_khachhang")
	val idKhachhang: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("madh")
	val madh: String? = null,

	@field:SerializedName("order_details")
	val orderDetails: List<OrderDetail>? = null,

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

data class OrderDetail(

	@field:SerializedName("product")
	val product: Product? = null,

	@field:SerializedName("id_donhang")
	val idDonhang: Int? = null,

	@field:SerializedName("giagoc")
	val giagoc: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("id_size")
	val idSize: Int? = null,

	@field:SerializedName("giaban")
	val giaban: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("soluong")
	val soluong: Int? = null,

	@field:SerializedName("id_sanpham")
	val idSanpham: Int? = null
)

data class Product(

	@field:SerializedName("id_loaisanpham")
	val idLoaisanpham: Int? = null,

	@field:SerializedName("hinhanh")
	val hinhanh: String? = null,

	@field:SerializedName("trangthai")
	val trangthai: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

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
