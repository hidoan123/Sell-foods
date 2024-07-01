package com.example.orderfood.cart

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.orderfood.R
import com.example.orderfood.databinding.LayoutItemCouponBinding
import com.example.orderfood.model.Coupon
import com.example.orderfood.network.RetrofitClient
import java.text.DecimalFormat

class CouponAdapter(var selectedPosition: Int, val itemClick: (Coupon, Boolean) -> Unit) :
    BaseQuickAdapter<Coupon, CouponAdapter.VH>() {


    class VH(
        parent: ViewGroup,
        val binding: LayoutItemCouponBinding = LayoutItemCouponBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: Coupon?) {
        item?.let {
            val formatter = DecimalFormat("#,###")
            val text = formatter.format(it.giamgia) + " VNĐ"
            Glide.with(holder.itemView.context)
                .load(RetrofitClient.baseImageCoupon + it?.hinhanh)
                .placeholder(R.drawable.coupon)
                .error(R.drawable.coupon)
                .into(holder.binding.imageCoupon)
            holder.binding.tiengiam.text = text
            holder.binding.tiengiam.setTextColor(Color.parseColor("#F40505"))
        }
        holder.itemView.setOnClickListener {
            if (selectedPosition == position) {
                selectedPosition = -1
                itemClick(item!!, true)
            } else {
                selectedPosition = position
                itemClick(item!!, false)
            }
            notifyDataSetChanged()
        }
        if (selectedPosition == position) {
            holder.binding.root.setBackgroundColor(context.getColor(R.color.gray))
        } else {
            holder.binding.root.setBackgroundColor(context.getColor(R.color.white))
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }
}