package com.example.orderfood.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orderfood.model.Category
import com.example.orderfood.model.FoodModel
import com.example.orderfood.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun getAllQuestion(): MutableLiveData<List<FoodModel>> {
        val listQuestion: MutableLiveData<List<FoodModel>> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient.getApiService().getAllProduct()
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
    //get top san pham
    fun getTopSanPham() : MutableLiveData<List<FoodModel>>{
        val listTopSanPham : MutableLiveData<List<FoodModel>> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient.getApiService().getTopSanPham()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                    Schedulers.io()
                ).subscribe(
                    {
                        listTopSanPham.postValue(it.data)
                    },{
                        Log.d("Get list top sản phẩm", "${it.message.toString()}")
                    }
                )
        )
        return listTopSanPham
    }
    //get category food
    fun getCategory(): MutableLiveData<List<Category>> {
        val listQuestion: MutableLiveData<List<Category>> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient.getApiService().getCategory()
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