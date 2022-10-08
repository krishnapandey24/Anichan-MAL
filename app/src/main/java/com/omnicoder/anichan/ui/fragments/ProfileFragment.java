package com.omnicoder.anichan.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.stateAdapters.ProfileFragmentStateAdapter;
import com.omnicoder.anichan.databinding.ProfileFragmentBinding;
import com.omnicoder.anichan.models.jikan.Favorites;
import com.omnicoder.anichan.models.jikan.JikanUserStatistic;
import com.omnicoder.anichan.models.jikan.UserData;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.ProfileViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment{
    private ProfileViewModel viewModel;
    private ProfileFragmentBinding binding;
    private static final String datePattern = "dd MMMM yyyy";
    String username;
    boolean showingFriend = false;
    LoadingDialog loadingDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        if (getArguments() != null) {
            username = getArguments().getString("username");
            showingFriend=getArguments().getBoolean("friend");
            if (username != null) {
                viewModel.fetchUserInfo(username);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: 25-Sep-22 Do something about the username
        loadingDialog = new LoadingDialog(this, getContext());
        loadingDialog.startLoading();
        viewModel.getUserInfo().observe(getViewLifecycleOwner(), jikanUserResponse -> {
            if(jikanUserResponse==null){
                // TODO: 29-Sep-22 Create error message
                Toast.makeText(getContext(),"Unable to find data",Toast.LENGTH_SHORT).show();
                if(showingFriend){
                    Navigation.findNavController(binding.getRoot()).navigateUp();
                }
            }else{
                UserData data = jikanUserResponse.getData();
                setUpTabLayout(data.getStatistics(), data.getFavorites());
                Picasso.get().load(data.getImages().getJpg().getImage_url()).into(binding.profileImageView);
                binding.userNameView.setText(data.getUsername());
                binding.joinedView.setText(formatDate(data.getJoined()));
                binding.locationView.setText(data.getLocation());
            }
            loadingDialog.stopLoading();

        });

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

    private void setUpTabLayout(JikanUserStatistic statistics, Favorites favorites) {
        String[] tabs = getResources().getStringArray(R.array.ProfileTabs);
        ViewPager2 viewPager = binding.viewPager;
        FragmentStateAdapter fragmentStateAdapter;
        fragmentStateAdapter = new ProfileFragmentStateAdapter(this, statistics, favorites, username, viewModel);
        viewPager.setAdapter(fragmentStateAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(tabs[position])).attach();
    }



}