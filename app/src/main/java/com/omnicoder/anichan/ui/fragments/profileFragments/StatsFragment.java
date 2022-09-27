package com.omnicoder.anichan.ui.fragments.profileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.FragmentStatsBinding;
import com.omnicoder.anichan.models.jikan.JikanUserStatistic;

public class StatsFragment extends Fragment {
    JikanUserStatistic statistics;
    FragmentStatsBinding binding;


    public StatsFragment(){
        //Empty constructor
    }
    public StatsFragment(JikanUserStatistic statistics) {
        this.statistics=statistics;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentStatsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            binding.textView.setText("Total: "+statistics.getAnime().getTotalEntries());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}