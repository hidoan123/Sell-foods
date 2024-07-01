package com.example.orderfood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.orderfood.base.BaseActivity
import com.example.orderfood.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val navController = navHostFragment!!.navController
        binding.bottomNavigation.setupWithNavController(navHostFragment!!.navController)


        // Kiểm tra Intent để điều hướng tới Fragment mong muốn
        if (intent != null) {
            val fragmentToOpen = intent.getStringExtra("fragment_to_open")
            when (fragmentToOpen) {
                "OrderHistoryFragment" -> {
                    navController.navigate(R.id.orderHistoryFragment)
                }
                // Thêm các case khác tương ứng với các Fragment khác nếu cần thiết
            }
        }

    }
}