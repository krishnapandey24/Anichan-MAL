package com.omnicoder.anichan.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.stateAdapters.ExploreFragmentStateAdapter;
import com.omnicoder.anichan.databinding.ExploreFragmentBinding;

import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreFragment extends Fragment{
    private ExploreFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=ExploreFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnClickListeners();
        setUpTabLayout();



    }

    private void setUpTabLayout() {
        String[] tabs=getResources().getStringArray(R.array.ExploreTabs);
        ViewPager2 viewPager=binding.viewPager;
        FragmentStateAdapter fragmentStateAdapter;
        fragmentStateAdapter=new ExploreFragmentStateAdapter(this);
        viewPager.setAdapter(fragmentStateAdapter);
        new TabLayoutMediator(binding.tabLayout, viewPager, (tab, position) -> tab.setText(tabs[position])).attach();
        viewPager.setUserInputEnabled(false);
    }

    private void setOnClickListeners() {
        binding.searchView.setOnClickListener(v-> Navigation.findNavController(v).navigate(ExploreFragmentDirections.actionExploreFragmentToSearchActivity()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }



}