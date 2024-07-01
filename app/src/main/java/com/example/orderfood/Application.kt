package com.example.orderfood

import android.app.Application
import com.example.orderfood.sharedPre.SharedPref
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class Application : Application() {

    var rxBus: PublishSubject<Any> = PublishSubject.create()
    fun getRxBus() = rxBus as Observable<Any>

    override fun onCreate() {
        super.onCreate()
        SharedPref.init(applicationContext)
    }

}