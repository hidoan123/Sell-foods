package com.example.orderfood.doithongtin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orderfood.model.ChangeAddress
import com.example.orderfood.model.ChangePass
import com.example.orderfood.model.ChangePhone
import com.example.orderfood.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DoiThongTinViewModel : ViewModel() {


    private val compositeDisposable = CompositeDisposable()


    fun changePhone(data: ChangePhone): MutableLiveData<Int> {
        val mutableLivtData = MutableLiveData<Int>()
        compositeDisposable.add(
            RetrofitClient.getApiService().changePhone(data)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.io()
                ).subscribe(
                    {
                        mutableLivtData.postValue(it.status)
                    },
                    {

                    }
                )
        )
        return mutableLivtData
    }

    fun changePass(data: ChangePass): MutableLiveData<Int> {
        val mutableLivtData = MutableLiveData<Int>()
        compositeDisposable.add(
            RetrofitClient.getApiService().changePass(data)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.io()
                ).subscribe(
                    {
                        mutableLivtData.postValue(it.status)
                    },
                    {

                    }
                )
        )
        return mutableLivtData
    }

    fun changeAddress(data: ChangeAddress): MutableLiveData<Int> {
        val mutableLivtData = MutableLiveData<Int>()
        compositeDisposable.add(
            RetrofitClient.getApiService().changeAddress(data)
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.io()
                ).subscribe(
                    {
                        mutableLivtData.postValue(it.status)
                    },
                    {
                        Log.d("test", "error")
                    }
                )
        )
        return mutableLivtData
    }

}