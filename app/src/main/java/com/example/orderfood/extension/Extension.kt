package com.example.orderfood.extension

import java.text.DecimalFormat

fun currencyFormatter(num: Int?): String {
    val formatter = DecimalFormat("###,###,### VNĐ")
    return formatter.format(num)
}