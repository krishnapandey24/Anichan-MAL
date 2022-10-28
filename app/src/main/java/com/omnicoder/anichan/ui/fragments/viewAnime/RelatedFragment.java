package com.omnicoder.anichan.ui.fragments.viewAnime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.omnicoder.anichan.viewModels.MangaDetailsViewModel;
import com.omnicoder.anichan.viewModels.ViewAnimeViewModel;
import com.omnicoder.anichan.databinding.FragmentSeasonDetailsBinding;


public class RelatedFragment extends Fragment {
    FragmentSeasonDetailsBinding binding;
    int id;
    ViewAnimeViewModel animeViewModel;
    MangaDetailsViewModel mangaViewModel;

    public RelatedFragment(int id, ViewAnimeViewModel animeViewModel) {
        this.id = id;
        this.animeViewModel = animeViewModel;
    }

    public RelatedFragment(int id, MangaDetailsViewModel mangaViewModel) {
        this.id = id;
        this.mangaViewModel = mangaViewModel;
    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentSeasonDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }






}