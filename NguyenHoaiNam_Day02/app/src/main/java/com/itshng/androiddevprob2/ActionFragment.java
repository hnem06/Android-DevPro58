package com.itshng.androiddevprob2;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ActionFragment extends Fragment {

    private ActionViewModel mViewModel;

    public static ActionFragment newInstance() {
        return new ActionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ActionViewModel.class);
        // TODO: Use the ViewModel
    }

}