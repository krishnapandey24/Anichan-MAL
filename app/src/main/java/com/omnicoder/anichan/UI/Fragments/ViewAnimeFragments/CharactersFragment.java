package com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.Adapters.RecommendationsAdapter;
import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.databinding.FragmentSeasonDetailsBinding;

import java.util.List;


public class CharactersFragment extends Fragment {
    List<Data> recommendations;
    FragmentSeasonDetailsBinding binding;


    public CharactersFragment(List<Data> recommendations) {
        this.recommendations=recommendations;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= FragmentSeasonDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context=getContext();
        RecommendationsAdapter adapter = new RecommendationsAdapter(context,recommendations);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.recyclerView.setAdapter(adapter);

    }




}