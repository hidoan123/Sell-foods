package com.example.orderfood.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.orderfood.R
import com.example.orderfood.databinding.LayoutItemTopSanphamBinding
import com.example.orderfood.model.FoodModel
import com.example.orderfood.network.RetrofitClient

class TopsanphamAdapter : BaseQuickAdapter<FoodModel, TopsanphamAdapter.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: LayoutItemTopSanphamBinding = LayoutItemTopSanphamBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)
    override fun onBindViewHolder(holder:VH, position: Int, item: FoodModel?) {
        Glide.with(holder.itemView.context)
            .load(RetrofitClient.baseImage + item?.hinhanh)
            .placeholder(R.drawable.img_default)
            .error(R.drawable.img_default)
            .into(holder.binding.imgTopSP)
        holder.binding.tvTopSanPham.text = item?.tensp
        holder.binding.tvGiaTopSanPham.text = item?.giaban.toString() + " VND"
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): VH {
        return VH(parent)
    }
}