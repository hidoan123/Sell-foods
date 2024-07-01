package com.example.orderfood.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private const val baseURL = "https://4e80-58-186-128-223.ngrok-free.app"
    const val baseImage = "$baseURL/uploads/product/"
    const val baseImageCategory = "$baseURL/uploads/categories/"
    const val baseImageCoupon = "$baseURL/uploads/coupons/"
    const val baseImageRecipes = "$baseURL/uploads/recipes/"
    fun getApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseURL)
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

}