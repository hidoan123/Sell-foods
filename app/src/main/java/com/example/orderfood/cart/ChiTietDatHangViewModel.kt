package com.example.orderfood.cart

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.orderfood.model.AddOrderDetail
import com.example.orderfood.model.AddOrderModel
import com.example.orderfood.model.CartModel
import com.example.orderfood.model.Coupon
import com.example.orderfood.model.OrderDetailHistory
import com.example.orderfood.model.UpdateTrangThai
import com.example.orderfood.network.RetrofitClient
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ChiTietDatHangViewModel(app: Application) : AndroidViewModel(app) {

    private val compositeDisposable = CompositeDisposable()
    private val liveData = MutableLiveData<String>()

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    fun addOrder(orderModel: AddOrderModel, list: ArrayList<CartModel>): MutableLiveData<String> {
        compositeDisposable.add(
            RetrofitClient.getApiService().addOrder(orderModel)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe({ data ->
                    if (data.result.equals("Data has been saved") && data.data != null) {
                        for (i in list.indices) {
                            Log.d("ten cac mon an duoc add vao detail", list[i].name)
                            addOrderDetail(
                                AddOrderDetail(
                                    idDonhang = data.data.id.toString(),
                                    idSanpham = list[i].idsp.toString(),
                                    idSize = "1",
                                    soluong = list[i].amount.toString(),
                                    giaban = list[i].price.toString(),
                                    giagoc = list[i].price.toString()
//                                    giaban = orderModel.giaban,
//                                    giagoc = orderModel.giagoc
                                ), list, i
                            )
                        }
                    }
                }, {
                    Toast.makeText(
                        context, "Có lỗi xảy ra, Vui lòng thử lại", Toast.LENGTH_SHORT
                    ).show()
                })
        )
        return liveData
    }

    private fun addOrderDetail(
        orderDetail: AddOrderDetail? = null,
        list: ArrayList<CartModel>,
        position: Int
    ): MutableLiveData<String> {
        compositeDisposable.add(
            RetrofitClient.getApiService().addOrderDetail(orderDetail)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe({
                    if (position == list.size - 1) {
                        liveData.postValue(it.Result)
                        Log.d("log ket qua cua add order detail", "${it.Result}")
                    }
                }, {
                    liveData.postValue("error")
                    Toast.makeText(
                        context, "Có lỗi xảy ra, Vui lòng thử lại", Toast.LENGTH_SHORT
                    ).show()
                })
        )
        return liveData
    }



//    fun addOrder(orderModel: AddOrderModel, list: ArrayList<CartModel>): MutableLiveData<String> {
//        compositeDisposable.add(
//            RetrofitClient.getApiService().addOrder(orderModel)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMapCompletable { data ->
//                    if (data.result == "Data has been saved" && data.data != null) {
//                        // Convert list of CartModel to list of AddOrderDetail
//                        val orderDetails = list.mapIndexed { index, cartModel ->
//                            AddOrderDetail(
//                                idDonhang = data.data.id.toString(),
//                                idSanpham = cartModel.id.toString(),
//                                idSize = "1",
//                                soluong = cartModel.amount.toString(),
//                                giaban = cartModel.price.toString(),
//                                giagoc = cartModel.price.toString()
//                            ) to index
//                        }
//                        // Use concatMapCompletable to ensure order of execution
//                        Observable.fromIterable(orderDetails)
//                            .concatMapCompletable { (orderDetail, index) ->
//                                RetrofitClient.getApiService().addOrderDetail(orderDetail)
//                                    .subscribeOn(Schedulers.io())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .ignoreElement()
//                                    .doOnComplete {
//                                        if (index == list.size - 1) {
//                                            liveData.postValue("Order details added successfully")
//                                        }
//                                    }
//                                    .doOnError { throwable ->
//                                        liveData.postValue("error")
//                                        Toast.makeText(context, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show()
//                                    }
//                            }
//                    } else {
//                        Completable.error(Throwable("Order creation failed"))
//                    }
//                }
//                .toSingle { "Order process completed" }
//                .subscribe(
//                    { /* Handle completion if needed */ },
//                    { error ->
//                        liveData.postValue("error")
//                        Toast.makeText(context, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show()
//                    }
//                )
//        )
//        return liveData
//    }
//
//
//    private fun addOrderDetail(
//        orderDetail: AddOrderDetail? = null,
//        list: ArrayList<CartModel>,
//        position: Int
//    ): MutableLiveData<String> {
//        compositeDisposable.add(
//            RetrofitClient.getApiService().addOrderDetail(orderDetail)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe({
//                    if (position == list.size - 1) {
//                        liveData.postValue(it.Result)
//                    }
//                }, {
//                    liveData.postValue("error")
//                    Toast.makeText(
//                        context, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT
//                    ).show()
//                })
//        )
//        return liveData
//    }








    fun getOrderDetail(id: Int): MutableLiveData<OrderDetailHistory> {
        val liveData = MutableLiveData<OrderDetailHistory>()
        compositeDisposable.add(
            RetrofitClient.getApiService().getOrderHistoryDetail(id = id)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe({
                    liveData.postValue(it.data!!)
                }, {

                })
        )
        return liveData
    }

    //get order detail after add



    fun cancelOrder(id: Int): MutableLiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        compositeDisposable.add(
            RetrofitClient.getApiService().cancelOrder(id = id)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe({
                    liveData.postValue(true)
                }, {
                    liveData.postValue(false)
                })
        )
        return liveData

    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getCoupon(): MutableLiveData<List<Coupon>> {
        val liveData = MutableLiveData<List<Coupon>>()
        compositeDisposable.add(
            RetrofitClient.getApiService().getCouPon()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe({
                    if (it != null && it.isNotEmpty()) {
                        liveData.postValue(it)
                    }
                }, {
                    Toast.makeText(
                        context, "Có lỗi xảy ra, Vui lòng thử lại", Toast.LENGTH_SHORT
                    ).show()
                })
        )
        return liveData
    }

    fun updateTrangThaiDonHang(data: UpdateTrangThai): MutableLiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        compositeDisposable.add(
            RetrofitClient.getApiService().updateThanhToan(data)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(
                    {
                        liveData.postValue(true)
                    }, {
                        liveData.postValue(false)
                    }
                )
        )
        return liveData
    }
}