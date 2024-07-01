package com.example.orderfood.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ArrayRes
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.orderfood.categorydetail.DetailCategoryActivity
import com.example.orderfood.databinding.FragmentHomeBinding
import com.example.orderfood.detail.DetailFoodActivity
import com.example.orderfood.model.Category
import com.example.orderfood.model.FoodModel
import com.youth.banner.indicator.CircleIndicator
import java.text.Normalizer


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: HomeAdapter
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var list: ArrayList<FoodModel>
    private lateinit var topSPAdapter : TopsanphamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        adapter = HomeAdapter()
        topSPAdapter = TopsanphamAdapter()
        categoryAdapter = CategoryAdapter() { category, getProduct ->
            if (getProduct) {
                adapter.submitList(getListProductByCategory(category))
            } else {
                adapter.submitList(this.list)
            }
        }

        imageAdapter = ImageAdapter(getListBanner())
        binding.banner.setAdapter(imageAdapter)
        binding.banner.addBannerLifecycleObserver(this)//添加生命周期观察者
            .setIndicator(CircleIndicator(context));
        adapter.setOnItemClickListener { _, _, position ->
//            val intent = Intent(context, DetailFoodActivity::class.java)
//            intent.putExtra("id", adapter.items[position].id)
//            intent.putExtra("tensp", adapter.items[position].tensp)
//            startActivity(intent)
            chuyenman(adapter.items[position].id,adapter.items[position].tensp)
            true
        }

        topSPAdapter.setOnItemClickListener{_,_,position ->
            chuyenman(topSPAdapter.items[position].id, topSPAdapter.items[position].tensp)
        }

        binding.recylerView.adapter = adapter
        binding.recyclerViewCategory.adapter = categoryAdapter
        binding.rcvTopSanPham.adapter = topSPAdapter

        viewModel.getAllQuestion().observe(viewLifecycleOwner) {
            this.list = it as ArrayList<FoodModel>
            adapter.submitList(it)

        }
        viewModel.getCategory().observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)

        }
        viewModel.getTopSanPham().observe(viewLifecycleOwner){
            topSPAdapter.submitList(it)
        }

        binding.edt.addTextChangedListener {
            if (it.toString().isBlank()) {
                adapter.submitList(this.list)
            } else {
                val list = getDanhSachTheoTen(it.toString())
                adapter.submitList(list)
            }

        }
        //get list top san pham
        //getTopSanPham()
    }

    private fun chuyenman(idcm: Int?, tensp: String?) {
        val intent = Intent(context, DetailFoodActivity::class.java)
            intent.putExtra("id", idcm)
            intent.putExtra("tensp", tensp)
             startActivity(intent)
    }
    //get top san pham
//    private fun getTopSanPham() {
//        binding.rcvTopSanPham.adapter = topSanPhamAdapter
//        viewModel.getTopSanPham().observe(viewLifecycleOwner){
//            topSanPhamAdapter.submitList(it)
//        }
//    }

    private fun getListProductByCategory(category: Category?): List<FoodModel> {
        val list = ArrayList<FoodModel>()
        this.list.forEach { food ->
            if (food.danhmuc?.tenloai.equals(category?.tenloai)) {
                list.add(food)
            }
        }
        return list
    }

    private fun getDanhSachTheoTen(text: String): ArrayList<FoodModel> {
        val list = ArrayList<FoodModel>()
        for (item in adapter.items) {
            if (removeAccent(item.tensp!!).contains(removeAccent(text))) {
                list.add(item)
            }
        }
        return list
    }

    private fun getListBanner(): List<String> {
        val list = ArrayList<String>()
        list.add("https://ik.imagekit.io/tvlk/blog/2017/01/30-mon-ngon-nuc-long-nhat-dinh-phai-thu-khi-toi-ha-noi-phan-1.jpg?tr=dpr-2,w-675")
        list.add("https://ik.imagekit.io/tvlk/blog/2017/01/30-mon-ngon-nuc-long-nhat-dinh-phai-thu-khi-toi-ha-noi-phan-2.jpg?tr=dpr-2,w-675")
        list.add("https://ik.imagekit.io/tvlk/blog/2017/01/30-mon-ngon-nuc-long-nhat-dinh-phai-thu-khi-toi-ha-noi-phan-3-1.jpg?tr=dpr-2,w-675")
        list.add("https://ik.imagekit.io/tvlk/blog/2017/01/30-mon-ngon-nuc-long-nhat-dinh-phai-thu-khi-toi-ha-noi-phan-4.jpg?tr=dpr-2,w-675")
        return list
    }

    fun removeAccent(input: String): String {
        val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
        val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val withoutDiacritics = regex.replace(normalized, "")
        return withoutDiacritics.replace(" ", "").toLowerCase()
    }

}