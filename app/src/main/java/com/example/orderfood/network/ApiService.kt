package com.example.orderfood.network

import com.example.orderfood.base.BaseResponse
import com.example.orderfood.model.AddOrder
import com.example.orderfood.model.AddOrderDetail
import com.example.orderfood.model.AddOrderModel
import com.example.orderfood.model.Category
import com.example.orderfood.model.ChangeAddress
import com.example.orderfood.model.ChangePass
import com.example.orderfood.model.ChangePhone
import com.example.orderfood.model.Coupon
import com.example.orderfood.model.DetailFood
import com.example.orderfood.model.FoodModel
import com.example.orderfood.model.Login
import com.example.orderfood.model.NhanXet
import com.example.orderfood.model.NhanXetItem
import com.example.orderfood.model.OrderDetailHistory
import com.example.orderfood.model.OrderHistory
import com.example.orderfood.model.Recipe
import com.example.orderfood.model.Register
import com.example.orderfood.model.ReviewProduct
import com.example.orderfood.model.UpdateTrangThai
import com.example.orderfood.model.UserData
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {

    @GET("/api/products")
    fun getAllProduct(): Single<BaseResponse<List<FoodModel>>>

    @GET("/api/categories")
    fun getCategory(): Single<BaseResponse<List<Category>>>

    @GET("/api/product/{id}")
    fun getDetailFood(
        @Path("id") id: Int?
    ): Single<BaseResponse<DetailFood>>

    @POST("/api/register")
    fun register(@Body data: Register): Single<BaseResponse<Any>>

    @POST("/api/login")
    fun login(@Body data: Login): Single<BaseResponse<UserData>>

    @GET("/api/orders")
    fun getOrderHistory(): Single<BaseResponse<List<OrderHistory>>>

    @GET("/api/order/{id}")
    fun getOrderHistoryDetail(@Path("id") id: Int): Single<BaseResponse<OrderDetailHistory>>

    @POST("/api/cancel_order/{id}")
    fun cancelOrder(@Path("id") id: Int): Single<Any>

    @POST("/api/changepass")
    fun changePass(@Body data: ChangePass): Single<BaseResponse<Any>>

    @POST("/api/changephone")
    fun changePhone(@Body data: ChangePhone): Single<BaseResponse<Any>>

    @POST("/api/changeaddress")
    fun changeAddress(@Body data: ChangeAddress): Single<BaseResponse<Any>>

    @POST("/api/add-order")
    fun addOrder(@Body data: AddOrderModel): Single<AddOrder>

    @POST("/api/addOrderDetails")
    fun addOrderDetail(@Body data: AddOrderDetail?): Single<BaseResponse<Any>>

    @POST("/api/products/{id})/reviews")
    fun addReview(@Path("id") id: Int?, @Body data: ReviewProduct): Single<BaseResponse<Any>>


    @GET("/api/coupons")
    fun getCouPon(): Single<List<Coupon>>

    @PUT("/api/update-order")
    fun updateThanhToan(@Body data: UpdateTrangThai): Single<BaseResponse<Any>>


    @GET("/api/recipes")
    fun getRecipes() : Single<BaseResponse<List<Recipe>>>

    @GET("api/nhanxetProduct/{id}")
    fun getAllNhanXet(@Path("id") id: Int?) : Single<BaseResponse<List<NhanXetItem>>>

    @POST("api/addnhanxet")
    fun addNhanXet(@Body data: NhanXet?) : Single<BaseResponse<Any>>

    @GET("/api/gettopsanpham")
    fun getTopSanPham() : Single<BaseResponse<List<FoodModel>>>

}