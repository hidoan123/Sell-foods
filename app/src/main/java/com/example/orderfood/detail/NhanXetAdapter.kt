package com.example.orderfood.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.orderfood.databinding.LayoutNhanxetItemBinding
import com.example.orderfood.model.NhanXetItem

class NhanXetAdapter() : BaseQuickAdapter<NhanXetItem, NhanXetAdapter.VH>() {


    class VH(
        parent: ViewGroup,
        val binding : LayoutNhanxetItemBinding = LayoutNhanxetItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
    ): RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder:VH, position: Int, item: NhanXetItem?) {
        item?.let {
            holder.binding.tvUserName.text = it.user_name ?: "Unknown"
            holder.binding.tvCommentContent.text = it.noi_dung ?: "0"
            holder.binding.tvCommentTime.text = it.ngay_nhan_xet ?: "0"
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ):VH {
        return VH(parent)
    }

}