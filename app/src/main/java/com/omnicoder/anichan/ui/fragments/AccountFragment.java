package com.omnicoder.anichan.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentAccountBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View.OnClickListener onClickListener= v -> {
            AccountFragmentDirections.ActionAccountFragmentToProfileFragment action = AccountFragmentDirections.actionAccountFragmentToProfileFragment(null);
            Navigation.findNavController(v).navigate(action);
        };

        binding.profileImageView.setOnClickListener(onClickListener);
        binding.userDetailsView.setOnClickListener(onClickListener);
    }
}