package com.example.orderfood.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.orderfood.R
import com.example.orderfood.base.BaseResponse
import com.example.orderfood.databinding.LayoutItemHomeBinding
import com.example.orderfood.extension.currencyFormatter
import com.example.orderfood.model.FoodModel
import com.example.orderfood.network.RetrofitClient

class HomeAdapter : BaseQuickAdapter<FoodModel, HomeAdapter.VH>() {


    class VH(
        parent: ViewGroup,
        val binding: LayoutItemHomeBinding = LayoutItemHomeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: FoodModel?) {
        Glide.with(holder.itemView.context)
            .load(RetrofitClient.baseImage + item?.hinhanh)
            .placeholder(R.drawable.img_default)
            .error(R.drawable.img_default)
            .into(holder.binding.img)
        holder.binding.tenSP.text = item?.tensp
        holder.binding.mota.text = item?.mota
        holder.binding.gia.text = currencyFormatter(item?.giaban)

    }



    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        // 返回一个 ViewHolder
        return VH(parent)
    }
}