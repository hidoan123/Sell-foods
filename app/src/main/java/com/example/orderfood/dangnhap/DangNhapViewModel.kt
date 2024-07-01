package com.example.orderfood.dangnhap

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orderfood.model.Login
import com.example.orderfood.model.UserData
import com.example.orderfood.network.RetrofitClient
import com.example.orderfood.sharedPre.SharedPref
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DangNhapViewModel(app: Application) : AndroidViewModel(app) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val compositeDisposable = CompositeDisposable()


    fun login(
        email: String,
        matkhau: String,
        device_key: String
    ): MutableLiveData<String> {
        Log.d("testing", "device_key: $device_key")
        val listQuestion: MutableLiveData<String> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient.getApiService().login(Login(email, matkhau, device_key))
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.io()
                ).subscribe(
                    {
                        if (it.data != null) {
                            val userData = it.data
                            userData.currentPassword = matkhau
                            SharedPref.init(context)
                            SharedPref.write(SharedPref.USER_DATA, Gson().toJson(userData))
                            listQuestion.postValue(it.message)
                        } else {
                            val userData = UserData()
                            userData.currentPassword = matkhau
                            SharedPref.init(context)
                            SharedPref.write(SharedPref.USER_DATA, Gson().toJson(userData))
                            listQuestion.postValue(it.message)
                        }

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