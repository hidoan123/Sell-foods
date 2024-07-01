package com.example.orderfood.doithongtin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.orderfood.R
import com.example.orderfood.base.BaseActivity
import com.example.orderfood.databinding.ActivityDoiThongTinBinding
import com.example.orderfood.model.ChangeAddress
import com.example.orderfood.model.ChangePass
import com.example.orderfood.model.ChangePhone
import com.example.orderfood.model.UserData
import com.example.orderfood.sharedPre.SharedPref
import com.google.gson.Gson

class DoiThongTinActivity : BaseActivity() {

    private lateinit var binding: ActivityDoiThongTinBinding
    private var type: String = ""
    private lateinit var viewModel: DoiThongTinViewModel
    private lateinit var userData: UserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoiThongTinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DoiThongTinViewModel::class.java]
        SharedPref.init(this)
        userData = Gson().fromJson(SharedPref.read(SharedPref.USER_DATA, ""), UserData::class.java)
        type = intent.getStringExtra("type").toString()
        when (type) {
            "doipass" -> {
                binding.inputLayoutMatKhauCu.visibility = View.VISIBLE
                binding.inputLayoutMatKhauMoi.visibility = View.VISIBLE
            }

            "doidiachi" -> {
                binding.inputLayoutDiaChi.visibility = View.VISIBLE
            }

            "doisodienthoai" -> {
                binding.inputLayoutSDT.visibility = View.VISIBLE
            }

            "doiten" -> {
                binding.inputLayoutTenMoi.visibility = View.VISIBLE
            }
        }

        binding.btnOK.setOnClickListener {
            when (type) {
                "doipass" -> {
                    if (binding.matkhaucu.text.toString() != userData.currentPassword) {
                        Toast.makeText(this, "Mật khẩu cũ chưa đúng", Toast.LENGTH_SHORT).show()
                    } else if (binding.matkhaumoi.text.toString()
                            .isBlank() || binding.matkhaucu.text.toString().isBlank()
                    ) {
                        Toast.makeText(
                            this, "Mật khẩu mới và cũ không được để trống", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val changePass = ChangePass(
                            id = userData.id,
                            current_password = userData.currentPassword,
                            new_password = binding.matkhaumoi.text.toString()
                        )
                        Log.d("testing", changePass.toString())
                        viewModel.changePass(
                            changePass
                        ).observe(this) {
                            if (it == 200) {
                                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT)
                                    .show()
                                userData.currentPassword = binding.matkhaumoi.text.toString()
                                SharedPref.write(SharedPref.USER_DATA, Gson().toJson(userData))
                                this.finish()
                            } else {
                                Toast.makeText(
                                    this, "Có lỗi xảy ra, Vui lòng thử lại", Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }

                }

                "doidiachi" -> {
                    if (binding.diachi.text.toString().isBlank()) {
                        Toast.makeText(
                            this, "Không được để trống địa chỉ", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.changeAddress(
                            ChangeAddress(
                                id = userData.id, address = binding.diachi.text.toString()
                            )
                        ).observe(this) {
                            if (it == 200) {
                                Toast.makeText(this, "Đổi địa chỉ thành công", Toast.LENGTH_SHORT)
                                    .show()
                                this.finish()
                            } else {
                                Toast.makeText(
                                    this, "Có lỗi xảy ra, Vui lòng thử lại", Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }
                }

                "doisodienthoai" -> {
                    if (binding.sodienthoai.text.toString().isBlank()) {
                        Toast.makeText(
                            this, "Không được để trống số điện thoại", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.changePhone(
                            ChangePhone(
                                id = userData.id, new_phone = binding.sodienthoai.text.toString()
                            )
                        ).observe(this) {
                            if (it == 200) {
                                Toast.makeText(
                                    this, "Đổi số điện thoại thành công", Toast.LENGTH_SHORT
                                ).show()
                                this.finish()
                            } else {
                                Toast.makeText(
                                    this, "Có lỗi xảy ra, Vui lòng thử lại", Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }
                }
            }
        }
    }
}