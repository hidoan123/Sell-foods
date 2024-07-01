package com.example.orderfood.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.orderfood.dangnhap.DangNhapActivity
import com.example.orderfood.databinding.FragmentProfileBinding
import com.example.orderfood.doithongtin.DoiThongTinActivity
import com.example.orderfood.model.UserData
import com.example.orderfood.sharedPre.SharedPref
import com.google.gson.Gson

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var userData: UserData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        SharedPref.init(context)
        userData = Gson().fromJson(SharedPref.read(SharedPref.USER_DATA, ""), UserData::class.java)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.email.text = "Email: " + userData.email
        binding.ten.text = "Tên: " + userData.nameStaff
        binding.dienthoai.text = "Số điện thoại: " + userData.phoneNumber
        viewModel.login(userData.email!!, userData.currentPassword!!).observe(this) {
            userData =
                Gson().fromJson(SharedPref.read(SharedPref.USER_DATA, ""), UserData::class.java)
            binding.email.text = "Email: " + userData.email
            binding.ten.text = "Tên: " + userData.nameStaff
            binding.dienthoai.text = "Số điện thoại: " + userData.phoneNumber
        }

        binding.doimatkhau.setOnClickListener {
            val intent = Intent(context, DoiThongTinActivity::class.java)
            intent.putExtra("type", "doipass")
            startActivity(intent)
        }
        binding.doisodienthoai.setOnClickListener {
            val intent = Intent(context, DoiThongTinActivity::class.java)
            intent.putExtra("type", "doisodienthoai")
            startActivity(intent)
        }
        binding.doiDiachi.setOnClickListener {
            val intent = Intent(context, DoiThongTinActivity::class.java)
            intent.putExtra("type", "doidiachi")
            startActivity(intent)
        }

        binding.dangxuat.setOnClickListener{
            val intent = Intent(context, DangNhapActivity::class.java)
            startActivity(intent)
        }


    }

}