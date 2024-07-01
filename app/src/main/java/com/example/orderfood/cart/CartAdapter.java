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
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<CartModel> list;


    public CartAdapter(List<CartModel> list) {
        this.list = list;
    }

    public void submitList(List<CartModel> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public List<CartModel> getList() {
        return list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
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
            price.setText(formatter.format(item.getPrice() * item.getAmount()) + " VNĐ");
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
                    onItemClickListener.onRemoveCart(position);
                }
            });
            radioButton.setOnClickListener(v -> {
                item.setCheck(!item.isCheck());
                onItemClickListener.onSelectOnProduct(radioButton.isChecked(), item.getId());
            });
        }
    }

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onRemoveCart(int position);

        void onSelectOnProduct(boolean isCheck, int id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}