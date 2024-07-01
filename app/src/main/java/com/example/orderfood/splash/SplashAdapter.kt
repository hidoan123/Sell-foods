package com.example.orderfood.splash

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.orderfood.databinding.LayoutItemSpalshBinding
import com.example.orderfood.model.SplashModel

class SplashAdapter : BaseQuickAdapter<SplashModel, SplashAdapter.ViewHolder>() {

    class ViewHolder(
        parent: ViewGroup,
        val binding: LayoutItemSpalshBinding = LayoutItemSpalshBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int, item: SplashModel?) {
        if (item != null) {
            holder.binding.image.setImageResource(item.image)
            holder.binding.title.text = item.title
            holder.binding.description.text = item.description
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(parent)
    }
}