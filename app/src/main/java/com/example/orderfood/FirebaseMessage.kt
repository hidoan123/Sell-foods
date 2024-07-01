package com.example.orderfood

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessage : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("testing", "onMessageReceived")
        (application as Application).rxBus.onNext("notificationFromSever")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("testing", "onNewToken $token")
    }


}