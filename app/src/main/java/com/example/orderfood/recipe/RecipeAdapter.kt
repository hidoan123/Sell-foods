package com.example.orderfood.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.orderfood.R
import com.example.orderfood.databinding.LayoutItemOrderHistoryBinding
import com.example.orderfood.databinding.LayoutItemRecipeBinding
import com.example.orderfood.model.Recipe
import com.example.orderfood.network.RetrofitClient

class RecipeAdapter : BaseQuickAdapter<Recipe, RecipeAdapter.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: LayoutItemRecipeBinding = LayoutItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: VH, position: Int, item: Recipe?) {
        Glide.with(holder.itemView.context)
            .load(RetrofitClient.baseImageRecipes + item?.anh)
            .placeholder(R.drawable.coupon)
            .error(R.drawable.coupon)
            .into(holder.binding.imgRecipe)
        holder.binding.tvNameRecipe.text = item?.ten_mon
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): VH {
        return VH(parent)
    }
}