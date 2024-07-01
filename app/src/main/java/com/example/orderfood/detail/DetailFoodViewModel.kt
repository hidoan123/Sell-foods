package com.example.orderfood.detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orderfood.Application
import com.example.orderfood.model.DetailFood
import com.example.orderfood.model.FoodModel
import com.example.orderfood.model.NhanXet
import com.example.orderfood.model.NhanXetItem
import com.example.orderfood.model.ReviewProduct
import com.example.orderfood.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailFoodViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var liveDataString = MutableLiveData<String>()
    fun getDetailFood(id: Int?): MutableLiveData<DetailFood> {
        val listQuestion: MutableLiveData<DetailFood> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient.getApiService().getDetailFood(id)
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

    fun addReview(id: Int?, star: Int): MutableLiveData<String> {
        val liveData: MutableLiveData<String> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient.getApiService().addReview(
                id, ReviewProduct(
                    id_sanpham = id,
                    danhgia = star
                )
            ).observeOn(AndroidSchedulers.mainThread()).subscribeOn(
                Schedulers.io()
            ).subscribe(
                {
                    liveData.postValue(it.message)
                }, {
                    liveData.postValue("error")
                }
            )
        )
        return liveData
    }

    //nhan xet
    fun getNhanXetPro(id : Int) : MutableLiveData<List<NhanXetItem>>{
        var listNhanXet : MutableLiveData<List<NhanXetItem>> = MutableLiveData()
        compositeDisposable.add(
            RetrofitClient
                .getApiService()
                .getAllNhanXet(id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        listNhanXet.postValue(it?.data)
                    },{
                        Log.d("Gọi api get nhận xét","Đã bị lỗi")
                    }
                )
        )
        return listNhanXet
    }

    // add nhan xet
    fun addNhanXet(nhanxet : NhanXet) : MutableLiveData<String>{
        compositeDisposable.add(
            RetrofitClient
                .getApiService()
                .addNhanXet(nhanxet).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        liveDataString.postValue("Data has been saved")
                    },
                    {
                        liveDataString.postValue("error")

                    }
                ))
        return liveDataString
    }
}