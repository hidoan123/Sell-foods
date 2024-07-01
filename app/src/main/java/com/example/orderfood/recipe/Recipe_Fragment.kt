package com.example.orderfood.recipe

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orderfood.R
import com.example.orderfood.databinding.FragmentOrderHistoryBinding
import com.example.orderfood.databinding.FragmentRecipeBinding
import com.example.orderfood.model.FoodModel
import com.example.orderfood.model.Recipe
import java.text.Normalizer


class Recipe_Fragment : Fragment() {
    private lateinit var binding : FragmentRecipeBinding
    private lateinit var viewModel : RecipeViewModel
    private lateinit var adapter : RecipeAdapter
    private var listRecipes = ArrayList<Recipe>() // Khởi tạo một ArrayList rỗng

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        adapter = RecipeAdapter()
        binding.rcvRecipes.adapter = adapter
        binding.rcvRecipes.layoutManager = LinearLayoutManager(requireContext())

        adapter.setOnItemClickListener {
            _,_,position ->
            val intent = Intent(context, ChitietRecipe::class.java)
            intent.putExtra("recipe_transfer", adapter.items[position])
            startActivity(intent)
        }

        viewModel.getAllRecipe().observe(viewLifecycleOwner){
            recipes->
            listRecipes.clear() // Xóa danh sách cũ
            listRecipes.addAll(recipes) // Thêm tất cả các recipe mới vào danh sách
            adapter.submitList(listRecipes)
        }

        binding.edtCongThuc.addTextChangedListener {
            if (it.toString().isBlank()) {
                adapter.submitList(this.listRecipes)
            } else {
                val listRecipes = getDanhSachTheoTen(it.toString())
                adapter.submitList(listRecipes)
            }

        }
    }



    private fun getDanhSachTheoTen(text: String): ArrayList<Recipe> {
        val list = ArrayList<Recipe>()
        for (item in adapter.items) {
            if (removeAccent(item.ten_mon!!).contains(removeAccent(text))) {
                list.add(item)
            }
        }
        return list
    }

    fun removeAccent(input: String): String {
        val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
        val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()
        val withoutDiacritics = regex.replace(normalized, "")
        return withoutDiacritics.replace(" ", "").toLowerCase()
    }


}