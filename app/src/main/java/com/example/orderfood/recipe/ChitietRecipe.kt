package com.example.orderfood.recipe

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.orderfood.R
import com.example.orderfood.base.BaseActivity
import com.example.orderfood.databinding.ActivityChiTietDonHangBinding
import com.example.orderfood.databinding.ActivityChitietRecipeBinding
import com.example.orderfood.model.Recipe
import com.example.orderfood.network.RetrofitClient

class ChitietRecipe : BaseActivity() {

    private lateinit var binding : ActivityChitietRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChitietRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recipe = intent.getSerializableExtra("recipe_transfer") as Recipe
        loadData(recipe)
    }

    private fun loadData(item : Recipe) {
        Glide.with(this)
            .load(RetrofitClient.baseImageRecipes + item?.anh)
            .placeholder(R.drawable.coupon)
            .error(R.drawable.coupon)
            .into(binding.imgInforRecipe)
        binding.tvRecipeName.text = item.ten_mon
        binding.tvNguyenLieu.text = item.nguyen_lieu
        binding.tvCachNau.text = item.cach_nau
    }
}