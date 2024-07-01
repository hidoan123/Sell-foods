package com.example.orderfood.dangki

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.orderfood.dangnhap.DangNhapActivity
import com.example.orderfood.databinding.ActivityDangKiBinding

class DangKiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDangKiBinding
    private lateinit var viewModel: DangKiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDangKiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DangKiViewModel::class.java]
        binding.btnDangKi.setOnClickListener {
            val email = binding.email.text.toString()
            val ten = binding.ten.text.toString()
            val matkhau = binding.matkhau.text.toString()
            val sodienthoai = binding.sodienthoai.text.toString()
            val diachi = binding.diachi.text.toString()
            viewModel.register(email, ten, matkhau, sodienthoai, diachi).observe(this) {
                if (it == 200) {
                    Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, DangNhapActivity::class.java)
                    startActivity(intent)
                    this.finish()
                } else if (it == 400) {
                    Toast.makeText(
                        this,
                        "Email đã tồn tại , hãy sử dụng email khác",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(this, "Có lỗi xảy ra vui lòng thử lại", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }
}