package com.example.orderfood.orderhistory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.orderfood.cart.ChiTietDonHangActivity
import com.example.orderfood.databinding.FragmentOrderHistoryBinding
import com.example.orderfood.model.OrderHistory
import com.example.orderfood.model.UserData
import com.example.orderfood.sharedPre.SharedPref
import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class OrderHistoryFragment : Fragment() {

    private lateinit var binding: FragmentOrderHistoryBinding
    private lateinit var adapter: OrderHistoryAdapter
    private lateinit var viewModel: OrderHistoryViewModel
    private lateinit var userData: UserData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[OrderHistoryViewModel::class.java]
        userData = Gson().fromJson(SharedPref.read(SharedPref.USER_DATA, ""), UserData::class.java)
        adapter = OrderHistoryAdapter()

        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(context, ChiTietDonHangActivity::class.java)
            intent.putExtra("hideButton", true)
            intent.putExtra("thanhtoan", adapter.items[position].trangthaithanhtoan)
            intent.putExtra("id", adapter.items[position].id)
            intent.putExtra("tongTien", adapter.items[position].tongtien)
            Log.d("testing", adapter.items[position].id.toString())
            startActivity(intent)
        }

//        viewModel.getOrderHistory().observe(viewLifecycleOwner) {
//            adapter.submitList(getOrderByEmail(it))
//        }
        setupSpinner()
    }

    private fun setupSpinner() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.getOrderHistory().observe(viewLifecycleOwner) {
                    val filteredList = when (position) {
                        0 -> getOrderByDay(it)
                        1 -> getOrderByLast7Days(it)
                        else -> getOrderByEmail(it)
                    }
                    adapter.submitList(filteredList)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    private fun getOrderByEmail(orderHistories: List<OrderHistory>): ArrayList<OrderHistory> {
        val list = ArrayList<OrderHistory>()
        orderHistories.forEach {
            if (it.email!! == userData.email) {
                list.add(it)
            }
        }
        return list
    }
    private fun getOrderByDay(orderHistories: List<OrderHistory>) : ArrayList<OrderHistory>{
        val list = ArrayList<OrderHistory>()
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        orderHistories.forEach {
            val orderDate = it.createdAt?.substring(0, 10)
            if (orderDate == today && it.email == userData.email) {
                list.add(it)
            }
        }
        return list

    }
    private fun getOrderByLast7Days(orderHistories: List<OrderHistory>): List<OrderHistory> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val last7Days = dateFormat.format(calendar.time)

        return orderHistories.filter {
            it.createdAt?.let { dateString ->
                try {
                    val date = dateFormat.parse(dateString)
                    val last7DaysDate = dateFormat.parse(last7Days)
                    date?.after(last7DaysDate) ?: false
                } catch (e: ParseException) {
                    Log.e("OrderHistoryFragment", "Error parsing date: $dateString", e)
                    false
                }
            } ?: false
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("Đây là on Resume", "Đã hiện lên gòy")
        setupSpinner()
    }
}