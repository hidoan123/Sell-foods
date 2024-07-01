package com.example.orderfood.home

import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.orderfood.R
import com.example.orderfood.databinding.LayoutItemCategoryBinding
import com.example.orderfood.model.Category
import com.example.orderfood.network.RetrofitClient

class CategoryAdapter(val onClickListener: (Category?, Boolean) -> Unit) :
    BaseQuickAdapter<Category, CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = -1

    class CategoryViewHolder(
        parent: ViewGroup,
        val binding: LayoutItemCategoryBinding = LayoutItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int, item: Category?) {
        Glide.with(holder.itemView.context)
            .load(RetrofitClient.baseImageCategory + item?.hinhanh)
            .placeholder(R.drawable.img_default)
            .error(R.drawable.img_default)
            .into(holder.binding.img)
        holder.binding.tentheloai.text = item?.tenloai
        holder.binding.root.setOnClickListener {

            if (selectedPosition == position) {
                selectedPosition = -1
                onClickListener(item, false)
            } else {
                selectedPosition = position
                onClickListener(item, true)
            }
            notifyDataSetChanged()
        }
        if (selectedPosition == position) {
            holder.binding.root.setCardBackgroundColor(context.getColor(R.color.gray))
        } else {
            holder.binding.root.setCardBackgroundColor(context.getColor(R.color.white))
        }

    }


    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(parent)
    }
}