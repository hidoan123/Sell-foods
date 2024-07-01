package com.example.orderfood.profile

import android.annotation.SuppressLint
import android.app.Application
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

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val compositeDisposable = CompositeDisposable()

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    fun login(
        email: String,
        matkhau: String,
    ): MutableLiveData<UserData> {
        val listQuestion: MutableLiveData<UserData> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient.getApiService().login(Login(email, matkhau))
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.io()
                ).subscribe(
                    {
                        val userData = it.data
                        userData.currentPassword = matkhau
                        SharedPref.init(context)
                        SharedPref.write(SharedPref.USER_DATA, Gson().toJson(userData))
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