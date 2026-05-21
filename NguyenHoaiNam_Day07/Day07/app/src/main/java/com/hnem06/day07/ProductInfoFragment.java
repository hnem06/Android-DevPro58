package com.hnem06.day07;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductInfoFragment extends Fragment {

    private static final String ARG_PRODUCT_ID = "product_id";
    private static final String ARG_NAME = "product_name";
    private static final String ARG_DESCRIPTION = "product_description";
    private static final String ARG_COOKING_TIME = "product_cooking_time";
    private static final String ARG_DIFFICULTY = "product_difficulty";
    private static final String ARG_SERVINGS = "product_servings";
    private static final String ARG_LIKED = "product_liked";

    private boolean currentLiked;
    private int productId;

    public static ProductInfoFragment newInstance(int id, String name, String description, int cookingTime,
            String difficulty, int servings, boolean liked) {
        ProductInfoFragment fragment = new ProductInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PRODUCT_ID, id);
        args.putString(ARG_NAME, name);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_COOKING_TIME, cookingTime);
        args.putString(ARG_DIFFICULTY, difficulty);
        args.putInt(ARG_SERVINGS, servings);
        args.putBoolean(ARG_LIKED, liked);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_info, container, false);

        TextView productName = view.findViewById(R.id.infoProductName);
        TextView productDescription = view.findViewById(R.id.productDesc);
        TextView cookingTime = view.findViewById(R.id.timeText);
        TextView difficulty = view.findViewById(R.id.dificultText);
        TextView servings = view.findViewById(R.id.numberPeople);
        ImageView backButton = view.findViewById(R.id.back_);
        ImageView lovedButton = view.findViewById(R.id.loved_);
        ImageView loveButton = view.findViewById(R.id.love_);

        // Gán dữ liệu - Bundle
        if (getArguments() != null) {
            productId = getArguments().getInt(ARG_PRODUCT_ID);
            productName.setText(getArguments().getString(ARG_NAME));
            productDescription.setText(getArguments().getString(ARG_DESCRIPTION));
            cookingTime.setText(String.valueOf(getArguments().getInt(ARG_COOKING_TIME)));
            difficulty.setText(getArguments().getString(ARG_DIFFICULTY));
            servings.setText(String.valueOf(getArguments().getInt(ARG_SERVINGS)));

            currentLiked = getArguments().getBoolean(ARG_LIKED, false);
            updateLikeUI(lovedButton, loveButton, currentLiked);
        }

        loveButton.setOnClickListener(v -> {
            currentLiked = true;
            updateLikeUI(lovedButton, loveButton, currentLiked);

            sendLikeResult();
        });

        lovedButton.setOnClickListener(v -> {
            currentLiked = false;
            updateLikeUI(lovedButton, loveButton, currentLiked);
            sendLikeResult();
        });

        backButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void updateLikeUI(ImageView lovedButton, ImageView loveButton, boolean liked) {
        if (liked) {
            lovedButton.setVisibility(View.VISIBLE);
            loveButton.setVisibility(View.GONE);
        } else {
            lovedButton.setVisibility(View.GONE);
            loveButton.setVisibility(View.VISIBLE);
        }
    }

    // Send trạng thái like qua BlankFragment 1
    private void sendLikeResult() {
        Bundle result = new Bundle();
        result.putInt("product_id", productId);
        result.putBoolean("liked", currentLiked);
        getParentFragmentManager().setFragmentResult("like_result", result);
    }
}
