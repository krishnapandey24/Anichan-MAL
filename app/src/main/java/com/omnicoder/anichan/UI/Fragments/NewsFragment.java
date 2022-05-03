package com.omnicoder.anichan.UI.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omnicoder.anichan.databinding.NewsFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class NewsFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        NewsFragmentBinding binding = NewsFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        return view;

    }



}