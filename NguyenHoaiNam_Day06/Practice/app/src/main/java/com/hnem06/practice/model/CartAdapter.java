package com.hnem06.practice.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hnem06.practice.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    ArrayList<Product> cartList;
    OnCartChangeListener listener;

    public CartAdapter(ArrayList<Product> cartList, OnCartChangeListener listener) {
        this.cartList = cartList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product p = cartList.get(position);
        holder.imgProduct.setImageResource(p.getImage());
        holder.txtName.setText(p.getName());
        holder.txtPrice.setText("$" + p.getPrice());
        holder.txtQuantity.setText(String.valueOf(p.getQuantity()));
        holder.txtRating.setText("4.5");

        holder.btnPlus.setOnClickListener(v -> {
            p.setQuantity(p.getQuantity() + 1);
            holder.txtQuantity.setText(String.valueOf(p.getQuantity()));
            if (listener != null)
                listener.onCartChanged();
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (p.getQuantity() > 0) {
                p.setQuantity(p.getQuantity() - 1);
                holder.txtQuantity.setText(String.valueOf(p.getQuantity()));

                if (p.getQuantity() == 0) {
                    int pos = holder.getAdapterPosition();
                    cartList.remove(pos);
                    notifyItemRemoved(pos);
                }

                if (listener != null)
                    listener.onCartChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName;
        TextView txtRating;
        TextView txtPrice;
        TextView txtQuantity;
        ImageView btnPlus;
        ImageView btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgCartProduct);
            txtName = itemView.findViewById(R.id.txtCartName);
            txtRating = itemView.findViewById(R.id.txtCartRating);
            txtPrice = itemView.findViewById(R.id.txtCartPrice);
            txtQuantity = itemView.findViewById(R.id.txtCartQuantity);
            btnPlus = itemView.findViewById(R.id.btnCartPlus);
            btnMinus = itemView.findViewById(R.id.subCart);
        }
    }
}
