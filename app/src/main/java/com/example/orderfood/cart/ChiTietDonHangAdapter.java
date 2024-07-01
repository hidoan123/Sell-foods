package com.example.orderfood.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderfood.R;
import com.example.orderfood.model.CartModel;
import com.example.orderfood.network.RetrofitClient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.CartViewHolder> {

    ArrayList<CartModel> list;


    public ChiTietDonHangAdapter(ArrayList<CartModel> list) {
        this.list = list;
    }

    public ArrayList<CartModel> getList() {
        return list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_chitietdonhang, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void initView() {

    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        private AppCompatCheckBox radioButton;
        private ImageView imageView2;
        private TextView name;
        private TextView description;
        private TextView price;
        private ImageView imgDelete;
        private TextView btnTru;
        private TextView tvSoLuong;
        private TextView btnCong;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            imageView2 = itemView.findViewById(R.id.imageView2);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            btnTru = itemView.findViewById(R.id.btnTru);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            btnCong = itemView.findViewById(R.id.btnCong);
        }

        public void onBind(int position) {
            CartModel item = list.get(position);
            Glide.with(itemView.getContext()).load(RetrofitClient.baseImage + item.getImage()).into(imageView2);
            radioButton.setChecked(item.isCheck());
            name.setText(item.getName());
            description.setText(item.getDescription());
            DecimalFormat formatter = new DecimalFormat("#,###");
            price.setText(formatter.format(item.getPrice()) + " VNĐ");
            tvSoLuong.setText(item.getAmount() + "");

            btnCong.setOnClickListener(view -> {
                int soluong = Integer.parseInt(tvSoLuong.getText().toString());
                soluong++;
                tvSoLuong.setText(soluong + "");
                item.setAmount(soluong);

            });

            btnTru.setOnClickListener(view -> {
                int soluong = Integer.parseInt(tvSoLuong.getText().toString());
                if (soluong == 1) {
                    return;
                }
                soluong--;
                tvSoLuong.setText(soluong + "");
                item.setAmount(soluong);

            });

            imgDelete.setOnClickListener(v -> {

                if (onItemClickListener != null) {
                    onItemClickListener.onClick(position);
                }
                if (onItemClickListener != null) {
                    onItemClickListener.setTotalPrice();
                }
            });
            radioButton.setOnClickListener(v -> {
                item.setCheck(!item.isCheck());
                if (onItemClickListener != null) {
                    onItemClickListener.setTotalPrice();
                }
            });
        }
    }

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);

        void setTotalPrice();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}