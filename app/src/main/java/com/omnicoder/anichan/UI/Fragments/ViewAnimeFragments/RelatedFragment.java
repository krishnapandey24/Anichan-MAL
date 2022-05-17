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

import com.omnicoder.anichan.Adapters.RelatedsAdapter;
import com.omnicoder.anichan.Models.AnimeResponse.RelatedAnime;
import com.omnicoder.anichan.databinding.FragmentSeasonDetailsBinding;

import java.util.List;


public class RelatedFragment extends Fragment {
    List<RelatedAnime> relatedAnime;
    FragmentSeasonDetailsBinding binding;

    public RelatedFragment(List<RelatedAnime> relatedAnime) {
        this.relatedAnime = relatedAnime;
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
        RelatedsAdapter adapter= new RelatedsAdapter(context,relatedAnime);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }






}