package com.example.orderfood.cart


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.orderfood.R
import com.example.orderfood.base.BaseActivity
import com.example.orderfood.databinding.ActivityChiTietDonHangBinding
import com.example.orderfood.model.AddOrderModel
import com.example.orderfood.model.CartModel
import com.example.orderfood.model.OrderDetailHistory
import com.example.orderfood.model.UpdateTrangThai
import com.example.orderfood.model.UserData
import com.example.orderfood.sharedPre.DatabaseHelper
import com.example.orderfood.sharedPre.SharedPref
import com.example.orderfood.thanhtoan.Api.CreateOrder
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date


class ChiTietDonHangActivity : BaseActivity() {

    private lateinit var binding: ActivityChiTietDonHangBinding
    private lateinit var adapter: ChiTietDonHangAdapter
    private lateinit var adapterV2: ChiTietDonHangAdapterV2
    private lateinit var list: ArrayList<CartModel>
    private lateinit var userData: UserData
    private lateinit var viewModel: ChiTietDatHangViewModel
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var orderDetail: OrderDetailHistory
    private lateinit var couponAdapter: CouponAdapter
    private var selectPos = -1
    private var giamgia = 0
    private var tongTien = "0"
    private var idCoupon = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChiTietDonHangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseHelper = DatabaseHelper(this)
        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX)
        SharedPref.init(this)
        viewModel = ViewModelProvider(this)[ChiTietDatHangViewModel::class.java]
        userData = Gson().fromJson(SharedPref.read(SharedPref.USER_DATA, ""), UserData::class.java)
        if (intent.getBooleanExtra("hideButton", false)) {
            binding.layoutDiaChi.visibility = View.GONE
            binding.btnDatHang.visibility = View.GONE
            binding.btnHuyDonHang.visibility = View.VISIBLE
            binding.tvMaGiamGia.visibility = View.GONE
            val tongTien = intent.extras?.get("tongTien")
            binding.tvTongTien.text =
                "Tổng tiền: " + DecimalFormat("#,###").format(tongTien) + "VNĐ"
            this.tongTien = tongTien.toString()
        }
        if (!intent.getBooleanExtra("hideButton", false)) {
            binding.layoutDiaChi.visibility = View.VISIBLE
            binding.tvMaGiamGia.visibility = View.VISIBLE
            binding.btnDatHang.visibility = View.VISIBLE
            list = intent.extras?.getSerializable("data") as ArrayList<CartModel>
            for(item in list){
                Log.d("Test item in list chi tiet don hang", "${item.name}")
            }
            adapter = ChiTietDonHangAdapter(list)
            binding.recyclerView.adapter = adapter

            binding.tennguoimua.text = "Tên người mua: " + userData.nameStaff
            binding.diachi.text = "Địa chỉ: " + userData.address
            binding.sodienthoai.text = "Số điện thoại: " + userData.phoneNumber
            binding.email.text = "Email: " + userData.email
            var tongTien = 0f
            for (i in adapter.list.indices) {
                tongTien += adapter.list[i].price * adapter.list[i].amount
            }

            binding.tvTongTien.text =
                "Tổng tiền: " + DecimalFormat("#,###").format(tongTien) + "VNĐ"
        }
        if (intent.extras?.get("id") != null) {
            binding.btnThanhToanZalo.visibility = View.VISIBLE
            val id = intent.extras?.get("id")
            viewModel.getOrderDetail(id as Int).observe(this) {
                orderDetail = it
                if (it.orderDetails != null) {
                    adapterV2 = ChiTietDonHangAdapterV2(it.orderDetails)
                    binding.recyclerView.adapter = adapterV2
                }
                binding.tennguoimua.text = "Tên người mua: " + it.hoten
                binding.diachi.text = "Địa chỉ: " + it.diachi
                binding.sodienthoai.text = "Số điện thoại: " + it.dienthoai
                binding.email.text = "Email: " + it.email
            }
        }
        if(intent.getIntExtra("thanhtoan",0)== 1){
            binding.btnThanhToanZalo.visibility = View.GONE
        }


        binding.btnDatHang.setOnClickListener {
            viewModel.addOrder(
                AddOrderModel(
                    madh = System.currentTimeMillis().toString(),
                    hoten = userData.nameStaff,
                    diachi = binding.edtDiaChi.text.toString().ifEmpty { userData.address },
                    email = userData.email,
                    ghichu = "",
                    dienthoai = userData.phoneNumber,
                    idNhanvien = null,
                    idKhachhang = userData.id,
                    httt = "12",
                    ngaytao = SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Date()),
                    tongdonhang = (getTongTien()+giamgia).toString(),
                    tongtien = getTongTien(),
                    idCoupon = idCoupon.toString(),
                    idFeeship = null,
                    trangthaithanhtoan = "0",
                    trangthai = "1",
                    //idSanPham = list[0].id.toString(),
                    //giaban = "20000",
                    //giagoc = "15000",
                    //soluong = list[0].amount.toString()
                ), adapter.list
            ).observe(this) {
                if (it.equals("Data has been saved")) {
                    binding.progressBar.visibility = View.VISIBLE
                    android.os.Handler().postDelayed({
                        binding.progressBar.visibility = View.GONE}, 2000)
                    for (i in adapter.list.indices) {
                        databaseHelper.removeCart(list[i].id)
                    }
                    Toast.makeText(this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
            }
        }

        binding.btnHuyDonHang.setOnClickListener {
            viewModel.cancelOrder(orderDetail.id!!).observe(this) {
                if (it) {
                    Toast.makeText(this, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show()
                    this.finish()
                } else {
                    Toast.makeText(this, "Hủy đơn hàng thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvMaGiamGia.setOnClickListener {
            viewModel.getCoupon().observe(this) {
                val dialog = Dialog(this, R.style.FullWidth)
                dialog.setContentView(R.layout.layout_coupon)
                val closeDialog = dialog.findViewById<TextView>(R.id.btnPick)
                closeDialog.setOnClickListener {
                    selectPos = couponAdapter.selectedPosition
                    dialog.dismiss()
                }
                val rv = dialog.findViewById<RecyclerView>(R.id.recyclerView)
                couponAdapter = CouponAdapter(selectPos) { coupon, select ->
                    if (couponAdapter.selectedPosition == -1) {
                        binding.tvMaGiamGia.text =
                            "Chọn mã giảm giá"
                        this.giamgia = 0
                    } else {
                        binding.tvMaGiamGia.text =
                            "Bạn đã chọn mã giảm giá : ${coupon.giamgia} VNĐ"
                        this.giamgia = coupon.giamgia!!
                        this.idCoupon = coupon.id!!
                    }

                }
                couponAdapter.submitList(it)
                rv.adapter = couponAdapter
                dialog.setCancelable(false)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.show()
            }
        }

        binding.btnThanhToanZalo.setOnClickListener {
            var token = ""
            lifecycleScope.launch {
                val job = lifecycleScope.async(Dispatchers.IO) {

                    val orderApi = CreateOrder()

                    try {
                        val data: JSONObject = orderApi.createOrder(tongTien)
                        val code = data.getString("return_code")
                        if (code == "1") {
                            token = data.getString("zp_trans_token")
                            return@async token
                        } else {
                            return@async token
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
                job.await()
                if (token == "") {
                    Toast.makeText(
                        this@ChiTietDonHangActivity,
                        "Có lỗi kết nối tới zalo pay, vui lòng thử lại",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.updateTrangThaiDonHang(
                        UpdateTrangThai(
                            id = orderDetail.id!!,
                            1
                        )
                    )
                        .observe(this@ChiTietDonHangActivity) {
                            if (it) {
                                Log.d("Đơn hàng thành công","ĐÃ thành công")
//                                Toast.makeText(
//                                    this@ChiTietDonHangActivity,
//                                    "Cập nhật trạng thái thanh toán đơn hàng thành công",
//                                    Toast.LENGTH_SHORT
//                                ).show()
                            } else {
                                Toast.makeText(
                                    this@ChiTietDonHangActivity,
                                    "Cập nhật trạng thái thanh toán đơn hàng thất bại",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ZaloPaySDK.getInstance()
                        .payOrder(
                            this@ChiTietDonHangActivity,
                            token,
                            "demozpdk://app",
                            object : PayOrderListener {
                                override fun onPaymentSucceeded(
                                    transactionId: String,
                                    transToken: String,
                                    appTransID: String
                                ) {
                                    runOnUiThread {
                                        AlertDialog.Builder(this@ChiTietDonHangActivity)
                                            .setTitle("Payment Success")
                                            .setMessage(
                                                String.format(
                                                    "TransactionId: %s - TransToken: %s",
                                                    transactionId,
                                                    transToken
                                                )
                                            )
                                            .setPositiveButton(
                                                "OK"
                                            ) { dialog, which -> }
                                            .setNegativeButton("Cancel", null).show()
                                    }
                                }

                                override fun onPaymentCanceled(
                                    zpTransToken: String,
                                    appTransID: String
                                ) {
                                    AlertDialog.Builder(this@ChiTietDonHangActivity)
                                        .setTitle("User Cancel Payment")
                                        .setMessage(
                                            String.format(
                                                "zpTransToken: %s \n",
                                                zpTransToken
                                            )
                                        )
                                        .setPositiveButton(
                                            "OK"
                                        ) { dialog, which -> }
                                        .setNegativeButton("Cancel", null).show()
                                }

                                override fun onPaymentError(
                                    zaloPayError: ZaloPayError,
                                    zpTransToken: String,
                                    appTransID: String
                                ) {
                                    AlertDialog.Builder(this@ChiTietDonHangActivity)
                                        .setTitle("Payment Fail")
                                        .setMessage(
                                            String.format(
                                                "ZaloPayErrorCode: %s \nTransToken: %s",
                                                zaloPayError.toString(),
                                                zpTransToken
                                            )
                                        )
                                        .setPositiveButton(
                                            "OK"
                                        ) { dialog, which -> }
                                        .setNegativeButton("Cancel", null).show()
                                }
                            })

                }
            }
        }
    }


    private fun getTongTien(): Int {
        var tongTien = 0f
        for (i in adapter.list.indices) {
            if (list[i].isCheck) {
                tongTien += list[i].amount * list[i].price
            }
        }
        return tongTien.toInt() - giamgia
    }





}