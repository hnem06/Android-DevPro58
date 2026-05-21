package com.hnem06.day07;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hnem06.day07.adapter.ProductAdapter;
import com.hnem06.day07.model.DifficultyEnum;
import com.hnem06.day07.model.ProductModel;

import java.util.ArrayList;

public class BlankFragment extends Fragment implements ProductAdapter.OnProductInfoClickListener {

    private BlankViewModel mViewModel;
    private ListView listProduct;
    private ProductAdapter productAdapter;
    private ArrayList<ProductModel> productList;

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        // Init ViewModel + get List Product
        mViewModel = new ViewModelProvider(this).get(BlankViewModel.class);
        productList = mViewModel.getProductList();

        listProduct = view.findViewById(R.id.listProduct);

        // set Adapter
        productAdapter = new ProductAdapter(getContext(), productList, this);
        listProduct.setAdapter(productAdapter);

        // Get like - ProductInfoFragment
        getParentFragmentManager().setFragmentResultListener("like_result", this, (requestKey, result) -> {
            int productId = result.getInt("product_id");
            boolean liked = result.getBoolean("liked");
            for (ProductModel p : productList) {
                if (p.getId() == productId) {
                    p.setLiked(liked);
                    break;
                }
            }
        });

        return view;
    }

    @Override
    public void onProductInfoClick(ProductModel product) {
        ProductInfoFragment fragment = ProductInfoFragment.newInstance(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCookingTime(),
                product.getDifficulty().name(),
                product.getServings(),
                product.isLiked());

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment1, fragment)
                .addToBackStack(null)
                .commit();
    }

}