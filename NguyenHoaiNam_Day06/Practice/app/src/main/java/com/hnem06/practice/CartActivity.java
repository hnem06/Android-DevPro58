package com.hnem06.practice;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hnem06.practice.model.CartAdapter;
import com.hnem06.practice.model.Product;
import com.hnem06.practice.model.ObjProduct2Json;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView rvCart;
    CartAdapter cartAdapter;
    ArrayList<Product> allProducts;
    ArrayList<Product> cartList;

    TextView tvItemCount, tvSubTotal, tvTotal;
    LinearLayout emptyState;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvCart = findViewById(R.id.rvCart);
        tvItemCount = findViewById(R.id.tvItemCount);
        tvSubTotal = findViewById(R.id.tvSubTotal);
        tvTotal = findViewById(R.id.tvTotal);
        emptyState = findViewById(R.id.emptyState);
        btnBack = findViewById(R.id.btnBack);


        allProducts = ObjProduct2Json.load(this);

        cartList = new ArrayList<>();
        if (allProducts != null) {
            for (Product p : allProducts) {
                if (p.getQuantity() > 0) {
                    cartList.add(p);
                }
            }
        }

        cartAdapter = new CartAdapter(cartList, () -> {
            updateCart();
            ObjProduct2Json.save(this, allProducts);
        });

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(cartAdapter);

        updateCart();

        btnBack.setOnClickListener(v -> finish());
    }

    private void updateCart() {
        int itemCount = cartList.size();
        int subTotal = 0;

        for (Product p : cartList) {
            subTotal += p.getPrice() * p.getQuantity();
        }

        tvItemCount.setText(itemCount + " Item(s)");
        tvSubTotal.setText(String.valueOf(subTotal));
        tvTotal.setText("$ " + subTotal);

        if (itemCount == 0) {
            emptyState.setVisibility(View.VISIBLE);
            rvCart.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rvCart.setVisibility(View.VISIBLE);
        }
    }
}