package com.example.orderfood.orderhistory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orderfood.model.OrderHistory
import com.example.orderfood.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrderHistoryViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun getOrderHistory(
    ): MutableLiveData<List<OrderHistory>> {
        val listQuestion: MutableLiveData<List<OrderHistory>> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient.getApiService().getOrderHistory()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.io()
                ).subscribe(
                    {
                        listQuestion.postValue(it.data)
                    },
                    {
                        Log.d("testing", it.message.toString())
                    }
                )
        )
        return listQuestion
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}