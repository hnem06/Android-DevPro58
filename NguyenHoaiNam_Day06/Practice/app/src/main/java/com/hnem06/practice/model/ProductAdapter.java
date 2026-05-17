package com.hnem06.practice.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hnem06.practice.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnQuantityChangeListener {
        void onQuantityChanged(int delta);
    }

    List<Product> list;
    OnQuantityChangeListener listener;

    public ProductAdapter(ArrayList<Product> list, OnQuantityChangeListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = list.get(position);
        holder.img.setImageResource(p.getImage());
        holder.txtName.setText(p.getName());
        holder.txtPrice.setText("$" + p.getPrice());
        holder.txtQuantity.setText(String.valueOf(p.getQuantity()));

        if (p.isLoved()) {
            holder.loved.setVisibility(View.VISIBLE);
            holder.love.setVisibility(View.GONE);
        } else {
            holder.loved.setVisibility(View.GONE);
            holder.love.setVisibility(View.VISIBLE);
        }

        holder.btnPlus.setOnClickListener(v -> {
            p.setQuantity(p.getQuantity() + 1);
            holder.txtQuantity.setText(String.valueOf(p.getQuantity()));
            if (listener != null)
                listener.onQuantityChanged(1);
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (p.getQuantity() > 0) {
                p.setQuantity(p.getQuantity() - 1);
                holder.txtQuantity.setText(String.valueOf(p.getQuantity()));
                if (listener != null)
                    listener.onQuantityChanged(-1);
            }
        });

        holder.love.setOnClickListener(v -> {
            p.setLoved(true);
            holder.loved.setVisibility(View.VISIBLE);
            holder.love.setVisibility(View.GONE);
        });

        holder.loved.setOnClickListener(v -> {
            p.setLoved(false);
            holder.loved.setVisibility(View.GONE);
            holder.love.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtName;
        TextView txtPrice;
        TextView txtQuantity;
        ImageView btnPlus;
        ImageView btnMinus;
        ImageView love;
        ImageView loved;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageProduct);
            txtName = itemView.findViewById(R.id.nameProduct);
            txtPrice = itemView.findViewById(R.id.priceProduct);
            txtQuantity = itemView.findViewById(R.id.quantity);
            btnPlus = itemView.findViewById(R.id.addProduct);
            btnMinus = itemView.findViewById(R.id.subProduct);
            love = itemView.findViewById(R.id.love);
            loved = itemView.findViewById(R.id.loved);
        }
    }
}
