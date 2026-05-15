package com.itshng.androiddevprob2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        View btnBack = findViewById(R.id.buttonBack);
        View btnInfo = findViewById(R.id.infoFragment);
        View btnAction = findViewById(R.id.actionFragment);


        btnBack.setOnClickListener(v -> {
//            Recursion
//            startActivity(new Intent(this, MainActivity.class));
            finish();
        });


        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString("text", "Hello, this is Fragment and not active");

            InfoFragment fragment = new InfoFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }




        btnInfo.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("text", "Hello, this is Info Fragment");

            InfoFragment infoFragment = new InfoFragment();
            infoFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, infoFragment)
                    .commit();
        });

        btnAction.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("text", "Hello, this is Action Fragment");

            ActionFragment actionFragment = new ActionFragment();
            actionFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                            .replace(R.id.fragment_container, actionFragment)
                                    .commit();

        });


    }
}