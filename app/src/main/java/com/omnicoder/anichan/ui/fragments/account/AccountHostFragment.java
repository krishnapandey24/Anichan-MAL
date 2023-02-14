package com.omnicoder.anichan.ui.fragments.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.AccountNavHostBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AccountHostFragment extends Fragment  {
    AccountNavHostBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=AccountNavHostBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }









}