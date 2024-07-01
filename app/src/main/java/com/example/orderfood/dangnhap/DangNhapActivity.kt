package com.example.orderfood.dangnhap

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.orderfood.MainActivity
import com.example.orderfood.dangki.DangKiActivity
import com.example.orderfood.databinding.ActivityDangNhapBinding
import com.example.orderfood.thanhtoan.Api.CreateOrder
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener


//import vn.zalopay.sdk.Environment
//import vn.zalopay.sdk.ZaloPayError
//import vn.zalopay.sdk.ZaloPaySDK
//import vn.zalopay.sdk.listeners.PayOrderListener

class DangNhapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDangNhapBinding
    private lateinit var viewModel: DangNhapViewModel
    private var deviceToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDangNhapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSDK()

        viewModel = ViewModelProvider(this)[DangNhapViewModel::class.java]

        binding.layoutDangKi.setOnClickListener {
            val intent = Intent(this, DangKiActivity::class.java)
            startActivity(intent)
        }

        binding.btnDangNhap.setOnClickListener {
            val email = binding.email.text.toString()
            val matkhau = binding.matkhau.text.toString()
            viewModel.login(email, matkhau, deviceToken).observe(this) {
                if (it.equals("success")) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    this.finish()
                } else {
                    Toast.makeText(this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.btnZaloPay.setOnClickListener {
            var token = ""
            lifecycleScope.launch {
                val job = lifecycleScope.async(Dispatchers.IO) {

                    val orderApi = CreateOrder()

                    try {
                        val data: JSONObject = orderApi.createOrder("100000")
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
                        this@DangNhapActivity,
                        "Có lỗi kết nối tới zalo pay, vui lòng thử lại",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    ZaloPaySDK.getInstance()
                        .payOrder(
                            this@DangNhapActivity,
                            token.toString(),
                            "demozpdk://app",
                            object : PayOrderListener {
                                override fun onPaymentSucceeded(
                                    transactionId: String,
                                    transToken: String,
                                    appTransID: String
                                ) {
                                    runOnUiThread {
                                        AlertDialog.Builder(this@DangNhapActivity)
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
                                    AlertDialog.Builder(this@DangNhapActivity)
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
                                    AlertDialog.Builder(this@DangNhapActivity)
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


    private fun initSDK() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
            if (!TextUtils.isEmpty(token)) {
                this.deviceToken = token
            }
        }.addOnFailureListener { e: Exception? -> }.addOnCanceledListener {}
            .addOnCompleteListener { task: Task<String> ->
                this.deviceToken = task.result
            }


        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX)
    }

}