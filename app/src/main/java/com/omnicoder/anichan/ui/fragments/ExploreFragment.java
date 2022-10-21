package com.omnicoder.anichan.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.adapters.stateAdapters.ExploreFragmentStateAdapter;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.activities.TestActivity;
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
        // TODO: 21-Oct-22 Change scan button to schedule
        binding.scanButton.setOnClickListener(v -> startActivity(new Intent(getContext(), TestActivity.class)));
        setUpTabLayout();

    }

    private void setUpTabLayout() {
        Fragment animeFragment=new ExploreAnimeFragment();
        Fragment mangaFragment=new ExploreMangaFragment();
        Fragment scheduleFragment=new ScheduleFragment();
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position= tab.getPosition();
                if(position==0){
                    getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,animeFragment).commit();
                }else if(position==1){
                    getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,mangaFragment).commit();
                }else{
                    getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,scheduleFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, animeFragment).commit();

    }

    private void setOnClickListeners() {
        binding.searchView.setOnClickListener(v-> Navigation.findNavController(v).navigate(ExploreFragmentDirections.actionExploreFragmentToSearchActivity()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        Log.d("tagg","destryped");
    }



}