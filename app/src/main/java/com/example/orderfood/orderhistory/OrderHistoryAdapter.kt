package com.example.orderfood.orderhistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.orderfood.R
import com.example.orderfood.databinding.LayoutItemOrderHistoryBinding
import com.example.orderfood.extension.currencyFormatter
import com.example.orderfood.model.OrderHistory

class OrderHistoryAdapter : BaseQuickAdapter<OrderHistory, OrderHistoryAdapter.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: LayoutItemOrderHistoryBinding = LayoutItemOrderHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: OrderHistory?) {
        holder.binding.madonhang.text = "Mã đơn hàng: " + item?.madh
        holder.binding.ngaydat.text = "Ngày đặt: " + item?.ngaytao
        holder.binding.gia.text = "Tổng tiền: " + currencyFormatter(item?.tongtien?.toInt())
        var text = ""
        var colorText = R.color.black
        when (item?.trangthai) {
            2 -> {
                text = "Trạng thái: Đang xử lí"
                colorText = R.color.processingColor
            }

            3 -> {
                text = "Trạng thái: Đang vận chuyển"
                colorText = R.color.shippingColor
            }

            4 -> {
                text = "Trạng thái: Đã giao"
                colorText = R.color.receivedColor
            }

            -1 -> {
                text = "Trạng thái: Đã hủy"
                colorText = R.color.cancelledColor
            }

            1 -> {
                text = "Trạng thái: Tiếp nhận"
                colorText = R.color.deliveredColor
            }
        }
        var ttthanhtoan = ""
        var colorTextPayment = R.color.gray
        when(item?.trangthaithanhtoan){
            0->{
                ttthanhtoan = "Trạng thái thanh toán: Chưa thanh toán"
                colorTextPayment = R.color.NotPayment
            }
            1->{
                ttthanhtoan = "Trạng thái thanh toán: Đã thanh toán"
                colorTextPayment = R.color.Payment
            }
        }
        holder.binding.trangthai.text = text
        holder.binding.trangthai.setTextColor(ContextCompat.getColor(holder.binding.root.context, colorText))
        holder.binding.trangthaithanhtoan.text = ttthanhtoan
        holder.binding.trangthaithanhtoan.setTextColor(ContextCompat.getColor(holder.binding.root.context, colorTextPayment))


    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }
}