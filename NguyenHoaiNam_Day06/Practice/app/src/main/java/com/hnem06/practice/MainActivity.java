package com.hnem06.practice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hnem06.practice.model.Product;
import com.hnem06.practice.model.ProductAdapter;
import com.hnem06.practice.model.ObjProduct2Json;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAdapter adapter;
    ArrayList<Product> list;
    TextView totalProduct;
    ImageView shoppingBag;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        totalProduct = findViewById(R.id.totalProduct);
        shoppingBag = findViewById(R.id.shopping_bag);

        list = ObjProduct2Json.load(this);
        if (list == null) {
            list = new ArrayList<>();
            list.add(new Product("Caramel Frappuchino", 20, R.drawable.caramel_frappucino_1, 0));
            list.add(new Product("Espresso", 2, R.drawable.espresso_1, 0));
            list.add(new Product("Hot Chocolate", 3, R.drawable.hot_chocolate_1, 0));
            list.add(new Product("Ice Coffee", 4, R.drawable.ice_coffee_1, 0));
            list.add(new Product("Latte", 5, R.drawable.mixed_black_coffee_1, 0));
            ObjProduct2Json.save(this, list);
        }

        adapter = new ProductAdapter(list, delta -> {
            total += delta;
            totalProduct.setText(String.valueOf(total));

            ObjProduct2Json.save(this, list);
        });

        recyclerView.setLayoutManager(
                new GridLayoutManager(this, 2));

        recyclerView.setAdapter(adapter);

        updateTotal();


        shoppingBag.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Khi quay lại từ CartActivity, load lại dữ liệu
        ArrayList<Product> updated = ObjProduct2Json.load(this);
        if (updated != null) {
            list.clear();
            list.addAll(updated);
            adapter.notifyDataSetChanged();
            updateTotal();
        }
    }

    private void updateTotal() {
        total = 0;
        for (Product p : list) {
            total += p.getQuantity();
        }
        totalProduct.setText(String.valueOf(total));
    }
}