package com.example.orderfood.dangki

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orderfood.model.DetailFood
import com.example.orderfood.model.Register
import com.example.orderfood.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DangKiViewModel : ViewModel() {


    private val compositeDisposable = CompositeDisposable()

    fun register(
        email: String,
        ten: String,
        matkhau: String,
        dienthoai: String,
        diachi: String
    ): MutableLiveData<Int> {
        val listQuestion: MutableLiveData<Int> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient.getApiService()
                .register(Register(email, ten, matkhau, dienthoai, diachi))
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.io()
                ).subscribe(
                    {
                        listQuestion.postValue(it.status)
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