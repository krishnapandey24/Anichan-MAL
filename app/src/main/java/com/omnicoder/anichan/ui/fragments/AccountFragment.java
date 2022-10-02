package com.omnicoder.anichan.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.omnicoder.anichan.databinding.FragmentAccountBinding;
import com.omnicoder.anichan.viewModels.AccountViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AccountFragment extends Fragment  {
    FragmentAccountBinding binding;
    AccountViewModel viewModel;
    private static final String datePattern = "dd MMMM yyyy";




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
        viewModel=new ViewModelProvider(this).get(AccountViewModel.class);
        viewModel.getUserInfo().observe(getViewLifecycleOwner(), userInfo -> {
            Picasso.get().load(userInfo.getPicture()).into(binding.profileImageView);
            binding.userNameView.setText(userInfo.getName());
            binding.joinedView.setText(formatDate(userInfo.getJoined_at()));
            binding.locationView.setText(userInfo.getLocation());
            View.OnClickListener onClickListener= v -> {
                AccountFragmentDirections.ActionAccountFragmentToProfileFragment action = AccountFragmentDirections.actionAccountFragmentToProfileFragment(userInfo.getName());
                Navigation.findNavController(v).navigate(action);
            };
            binding.profileImageView.setOnClickListener(onClickListener);
            binding.userDetailsView.setOnClickListener(onClickListener);
        });
        viewModel.fetchUserInfo();

    }



    private String formatDate(String joinedDate) {
        if (joinedDate == null) {
            return "---";
        }
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatter.parse(joinedDate);
            SimpleDateFormat newFormat = new SimpleDateFormat(datePattern, Locale.getDefault());
            assert date != null;
            return newFormat.format(date);
        } catch (Exception e) {
            return joinedDate.substring(0, 10);
        }
    }



}