package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class AddOrderModel(

    @field:SerializedName("tongtien")
    val tongtien: Int? = null,

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

    @field:SerializedName("id_feeship")
    val idFeeship: String? = null,

    @field:SerializedName("hoten")
    val hoten: String? = null,

    @field:SerializedName("httt")
    val httt: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("id_sanpham")
    val idSanPham: String? = null,

    @field:SerializedName("soluong")
    val soluong: String? = null,

    @field:SerializedName("giaban")
    val giaban: String? = null,

    @field:SerializedName("giagoc")
    val giagoc: String? = null,
)
