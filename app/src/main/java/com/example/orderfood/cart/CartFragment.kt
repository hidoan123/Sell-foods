package com.example.orderfood.cart

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.orderfood.databinding.FragmentCartBinding
import com.example.orderfood.model.CartModel
import com.example.orderfood.model.UserData
import com.example.orderfood.sharedPre.DatabaseHelper
import com.example.orderfood.sharedPre.SharedPref
import com.google.gson.Gson


class CartFragment : Fragment(), CartAdapter.OnItemClickListener {

    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var userData: UserData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        adapter = CartAdapter(fakeList())
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SharedPref.init(context)
        userData = Gson().fromJson(SharedPref.read(SharedPref.USER_DATA, ""), UserData::class.java)
        databaseHelper = DatabaseHelper(requireContext())

        binding.btnDatHang.setOnClickListener {
            if (getListCheck().size > 0) {
                val intent = Intent(context, ChiTietDonHangActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("data", getListCheck())
                for(item in getListCheck()){
                    Log.d("Test data check for list", "${item.name}")
                }
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                Toast.makeText(context, "Vui lòng chọn sản phẩm để order", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

    private fun getListCheck(): ArrayList<CartModel> {
        val list = ArrayList<CartModel>()
        for (item in adapter.list) {
            if (item.isCheck) {
                list.add(item)
            }
        }
        return list
    }

    private fun fakeList(): ArrayList<CartModel> = databaseHelper.getAllCart(userData.email!!)

    override fun onRemoveCart(position: Int) {
        AlertDialog.Builder(requireContext()).setMessage("Bạn có muốn xóa giỏ hàng không?")
            .setPositiveButton("Có") { _, _ ->
                removeCart(position)
            }.setNegativeButton("Không", null).setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    private fun removeCart(position: Int) {
        val result = databaseHelper.removeCart(adapter.list[position].id)
        if (result != -1) {
            Toast.makeText(context, "Xóa giỏ hàng thành công", Toast.LENGTH_SHORT).show()
            adapter.submitList(databaseHelper.getAllCart(userData.email!!))
        }
    }

    override fun onSelectOnProduct(isCheck: Boolean, id: Int) {
//        if (isCheck) {
//            for (i in adapter.list.indices) {
//                val it = adapter.list[i]
//                if (id != it.id) {
//                    it.isCheck = false
//                }
//            }
//        }
//        adapter.notifyDataSetChanged()
    }
}