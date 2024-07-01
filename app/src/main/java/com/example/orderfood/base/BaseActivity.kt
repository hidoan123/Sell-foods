package com.example.orderfood.base

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.orderfood.Application
import com.example.orderfood.R
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.orderfood.MainActivity

open class BaseActivity : AppCompatActivity() {

    private lateinit var dialog: AlertDialog.Builder
    private val CHANNEL_ID  = "order_update_channel"
    private val REQUEST_CODE_POST_NOTIFICATIONS = 1
    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()
        val bus = (this.application as Application).getRxBus()
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
            if (!TextUtils.isEmpty(token)) {
                Log.d("testing", " $token")
            } else {
                Log.d("testing", " $token")
            }
        }.addOnFailureListener { e: Exception? -> }.addOnCanceledListener {}
            .addOnCompleteListener { task: Task<String> ->
                Log.d("testing", " ${task.result}")
            }

        bus.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        sendNotification()
                    } else {
                        requestNotificationPermission()
                    }
                    //showDialog()
                    Log.d("testing", "nhận thông báo")
                },
                {
                    Log.d("testing", "không nhận thông báo")
                }
            )
    }

    private fun sendNotification() {
        createNotificationChannel()

        val largeIcon: Bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.iconshop)


        // Tạo intent để mở OrderHistoryActivity khi nhấn vào thông báo
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("fragment_to_open", "OrderHistoryFragment")
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.iconnotify) // Đảm bảo bạn có biểu tượng này trong thư mục res/drawable
            .setContentTitle("Cập nhật đơn hàng")
            .setContentText("Đơn hàng của bạn đã được admin cập nhật, Vui lòng vào tab Lịch sử đơn hàng để theo dõi đơn hàng")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(largeIcon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Cập nhật đơn hàng"
            val descriptionText = "Kênh để cập nhật đơn hàng"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_POST_NOTIFICATIONS)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                sendNotification()
            } else {
                Log.d("testing", "Quyền thông báo bị từ chối")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }




}