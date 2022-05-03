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

import com.omnicoder.anichan.Adapters.SeasonDetailsAdapter;
import com.omnicoder.anichan.Models.ViewAnime;
import com.omnicoder.anichan.databinding.FragmentSeasonDetailsBinding;


public class SeasonDetailsFragment extends Fragment {
    ViewAnime seasons;
    FragmentSeasonDetailsBinding binding;

    public SeasonDetailsFragment(ViewAnime seasons) {
        this.seasons = seasons;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentSeasonDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context= getContext();
        SeasonDetailsAdapter adapter= new SeasonDetailsAdapter(context,seasons.getSeasons(),seasons.getId());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }






}