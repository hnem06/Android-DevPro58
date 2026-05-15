package com.itshng.androiddevprob2;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InfoFragment extends Fragment {

    private InfoViewModel mViewModel;

    private static final String TAG = "Fragment-Debug";

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate : called");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView : called");

        View view = inflater.inflate(R.layout.fragment_info, container, false);

        String name = "";
        if (getArguments() != null) {
            name = getArguments().getString("text");
        }

        TextView tv = view.findViewById(R.id.textView);

        tv.setText(name);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated : called");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InfoViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart : called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume : called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause : called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop : called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView : called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy : called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach : called");
    }



}