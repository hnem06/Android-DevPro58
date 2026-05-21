package com.hnem06.day07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnem06.day07.R;
import com.hnem06.day07.model.ProductModel;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ProductModel> productList;
    private OnProductInfoClickListener listener;

    // Interface callback - khi bấm productInfo
    public interface OnProductInfoClickListener {
        void onProductInfoClick(ProductModel product);
    }

    public ProductAdapter(Context context, ArrayList<ProductModel> productList, OnProductInfoClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_ui, parent, false);
            holder = new ViewHolder();
            holder.productIcon = view.findViewById(R.id.productIcon);
            holder.productName = view.findViewById(R.id.productName);
            holder.productCookingTime = view.findViewById(R.id.peoductCookingTime);
            holder.productRate = view.findViewById(R.id.productRate);
            holder.productInfo = view.findViewById(R.id.productInfo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ProductModel product = productList.get(position);

        holder.productName.setText(product.getName());
        holder.productCookingTime.setText(product.getCookingTime() + " mins");
        holder.productRate.setText(String.valueOf(product.getRate()));

        holder.productInfo.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductInfoClick(product);
            }
        });

        return view;
    }

    private static class ViewHolder {
        ImageView productIcon;
        TextView productName;
        TextView productCookingTime;
        TextView productRate;
        ImageView productInfo;
    }
}
