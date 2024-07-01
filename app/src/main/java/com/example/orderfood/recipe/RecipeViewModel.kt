package com.example.orderfood.recipe

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.orderfood.model.Recipe
import com.example.orderfood.network.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RecipeViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun getAllRecipe() : MutableLiveData<List<Recipe>>{
        var listRecipe : MutableLiveData<List<Recipe>> = MutableLiveData()

        compositeDisposable.add(
                RetrofitClient
                .getApiService()
                .getRecipes().observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            listRecipe.postValue(it.data)
                        },{
                            Log.d("Gọi api get recipes","Đã bị lỗi")
                        }
                    ))
        return listRecipe
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}