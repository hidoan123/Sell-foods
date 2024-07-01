package com.example.orderfood.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.orderfood.dangnhap.DangNhapActivity
import com.example.orderfood.databinding.ActivitySplashBinding
import com.example.orderfood.model.SplashModel
import io.reactivex.annotations.NonNull


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var splashAdapter: SplashAdapter
    private var list: ArrayList<SplashModel> = ArrayList()

    private var currentPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClick()
        initIntro()
        splashAdapter = SplashAdapter()
        splashAdapter.submitList(list)
        binding.recyclerView.adapter = splashAdapter
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.recyclerView)
        binding.indicator.attachToRecyclerView(binding.recyclerView, pagerSnapHelper)

    }

    private fun setOnClick() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition: Int =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                currentPage = firstVisibleItemPosition
                Log.d("TAG", "onScrolled: $currentPage")
                if (currentPage == list.size - 1) {
                    binding.btnNext.text = "DONE"
                } else {
                    binding.btnNext.text = "NEXT"
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (currentPage == list.size - 1) {
                this.finish()
                val intent = Intent(this, DangNhapActivity::class.java)
                startActivity(intent)
            } else {
                currentPage++
                binding.recyclerView.scrollToPosition(currentPage)

            }
        }
    }

    private fun initIntro() {
        list.add(
            SplashModel(
                com.example.orderfood.R.drawable.intro1,
                "Chào mừng bạn đến với ứng dụng đặt đồ ăn của chúng tôi!",
                " Chúng tôi rất vui mừng được phục vụ bạn. Với ứng dụng này, bạn có thể dễ dàng tìm kiếm và đặt hàng các món ăn ngon từ nhiều nhà hàng và quán ăn trên khắp khu vực. Hãy cùng khám phá những tiện ích và trải nghiệm tuyệt vời mà ứng dụng của chúng tôi mang lại!"
            )
        )
        list.add(
            SplashModel(
                com.example.orderfood.R.drawable.intro3,
                "Tại sao nên sử dụng ứng dụng đặt đồ ăn của chúng tôi?",
                "Bạn có bao giờ cảm thấy bận rộn và không có thời gian để nấu ăn hoặc ra ngoài ăn? Với ứng dụng đặt đồ ăn của chúng tôi, mọi thứ trở nên dễ dàng hơn bao giờ hết. Bạn chỉ cần mở ứng dụng, lựa chọn món ăn yêu thích của mình và đặt hàng"
            )
        )
        list.add(
            SplashModel(
                com.example.orderfood.R.drawable.intro2,
                "Lợi ích khi sử dụng ứng dụng đặt đồ ăn của chúng tôi",
                "Lựa chọn phong phú: Ứng dụng của chúng tôi cung cấp rất nhiều sự lựa chọn về món ăn từ các nhà hàng uy tín và địa điểm phổ biến.\n" + "Tiết kiệm thời gian: Bạn có thể đặt đồ ăn ngay từ điện thoại của mình mà không cần phải ra ngoài."
            )
        )
    }
}