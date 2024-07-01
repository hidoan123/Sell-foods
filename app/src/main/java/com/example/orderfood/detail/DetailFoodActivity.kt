package com.example.orderfood.detail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.orderfood.R
import com.example.orderfood.base.BaseActivity
import com.example.orderfood.databinding.ActivityDetailFoodBinding
import com.example.orderfood.extension.currencyFormatter
import com.example.orderfood.model.CartModel
import com.example.orderfood.model.DetailFood
import com.example.orderfood.model.NhanXet
import com.example.orderfood.model.UserData
import com.example.orderfood.network.RetrofitClient
import com.example.orderfood.sharedPre.DatabaseHelper
import com.example.orderfood.sharedPre.SharedPref
import com.google.gson.Gson
import okhttp3.internal.filterList
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.tanh

class DetailFoodActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailFoodBinding
    private lateinit var viewModel: DetailFoodViewModel
    var id: Int = -1
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var detailFood: DetailFood
    private lateinit var userData: UserData
    private var canAddCart = false
    private lateinit var adapter: NhanXetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userData = Gson().fromJson(SharedPref.read(SharedPref.USER_DATA, ""), UserData::class.java)
        setOnClick()
        binding.title.text = "Chi tiết sản phẩm"
        viewModel = ViewModelProvider(this)[DetailFoodViewModel::class.java]
        databaseHelper = DatabaseHelper(this)
        id = intent.extras?.getInt("id") ?: -1
        getDetailFood()
        getNhanXet(id)
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setOnClick() {
        binding.addCart.setOnClickListener {
            if (canAddCart) {
                val image = detailFood.hinhanh
                val name = detailFood.tensp
                val description = detailFood.mota
                val price = detailFood.giaban?.toLong()
                val amount = 1
                val cart = CartModel()
                cart.image = image
                cart.name = name
                cart.description = description
                cart.price = price
                cart.amount = amount
                cart.idsp = detailFood.id!!
                val result = databaseHelper.addCart(
                    cart, userData.email!!
                )
                if (result.toFloat() != -1F) {
                    Toast.makeText(
                        this, "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_SHORT
                    ).show()
                } else if (result.toFloat() != -2F) {
                    Toast.makeText(
                        this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this, "Thêm sản phẩm vào giỏ hàng thất bại", Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this, "Hãy đợi sản phẩm tải xong", Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding.back.setOnClickListener {
            this.finish()
        }
        //review sao
        binding.review.setOnClickListener {
            if (!canAddCart) {
                Toast.makeText(
                    this, "Hãy đợi sản phẩm tải xong", Toast.LENGTH_SHORT
                ).show()
            } else {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.layout_dialog_review)
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                val btnok = dialog.findViewById<TextView>(R.id.btnok)
                btnok.setOnClickListener {
                    viewModel.addReview(
                        detailFood.id,
                        (dialog.findViewById<RatingBar>(R.id.ratingBar)!!).rating.toInt()
                    ).observe(this) {
                        if (it == "Đánh giá đã được thêm thành công") {
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        } else {
                            Toast.makeText(
                                this,
                                "Có lỗi xảy ra vui lòng thử lại",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                dialog.findViewById<ImageView>(R.id.close).setOnClickListener {
                    dialog.dismiss()
                }
                dialog.setCancelable(false)
                dialog.show()
            }

        }
        //btn nhan xet


        binding.edtNhanxet.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val rightDrawable = binding.edtNhanxet.compoundDrawablesRelative[2] // 2 là index của drawableRight
                rightDrawable?.let{
                    val drawableWidth = rightDrawable.bounds.width()

                    if(event.rawX >= (binding.edtNhanxet.right - drawableWidth)){
                        val commentText = binding.edtNhanxet.text.toString()
                        val time = getCurrentDateTime()
                        if(commentText.isNotEmpty()){
                            binding.progressBar.visibility = View.VISIBLE
                            val nhanxet = NhanXet(userData.id!!, id, commentText,time)
                            Log.d("Đây là id user", "${nhanxet.id_user}")
                            Log.d("Đây là id product", "${id}")
                            Log.d("Đây là comment ", "$commentText")
                            Log.d("Đây là thời gian", "$time")
                            sendComment(nhanxet)

                        }else{
                            Log.d("Comment Click", "Comment is empty")
                        }
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    private fun sendComment(nhanxet: NhanXet) {
        viewModel.addNhanXet(nhanxet).observe(this){
            if(it.equals("Data has been saved")){
                Log.d("Gọi thêm nhân xét","Đã thành công")

            }else{
                Log.d("Gọi thêm nhận xét","Đã thất bại")
            }
        }
        android.os.Handler().postDelayed({
            binding.progressBar.visibility = View.GONE
            Log.d("Comment", "Bình luận đã được gửi")
            binding.NScView.smoothScrollTo(0, 0)
            binding.edtNhanxet.clearFocus()
            hideKeyboard(this, binding.edtNhanxet)
            binding.edtNhanxet.text.clear()
            viewModel.getNhanXetPro(id).observe(this){
                adapter.submitList(it)
            }

        },2000)

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_dialog_ok)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val btnOKDiaLog = dialog.findViewById<Button>(R.id.OK)
        btnOKDiaLog.setOnClickListener{
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }
    // an ban phim
    private fun hideKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //chi tiet do an
    private fun getDetailFood() {
        viewModel.getDetailFood(id).observe(this) {
            canAddCart = true
            this.detailFood = it
            Glide.with(this).load(RetrofitClient.baseImage + it.hinhanh)
                .placeholder(R.drawable.img_default).error(R.drawable.img_default).into(binding.img)

            binding.ten.text = it.tensp
            binding.mota.text = it.mota
            binding.gia.text = currencyFormatter(it.giaban)
            var star = 0
            if (it.reviews.size > 0) {
                it.reviews.forEach { review ->
                    star += review.danhgia
                }
                star /= (it.reviews.size)
            } else {
                star = 5
            }
            binding.tvStar.text = star.toString()
        }
    }
    // nhan xet
    private fun getNhanXet(idProduct : Int) {
        adapter = NhanXetAdapter()
        binding.rcvNhanxet.adapter = adapter
        viewModel.getNhanXetPro(idProduct).observe(this){
                adapter.submitList(it)
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d("Đây là hàm pause được gọi","Đã được gọi")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Đây là hàm resume được gọi","Đã được gọi")
    }

        //get time currently
    fun getCurrentDateTime(): String {
        // Lấy thời gian hiện tại
        val currentTime = Date()

        // Định dạng chuỗi ngày/tháng/năm giờ:phút
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        // Format thời gian hiện tại thành chuỗi
        val formattedDateTime = dateFormat.format(currentTime)

        return formattedDateTime
    }
}