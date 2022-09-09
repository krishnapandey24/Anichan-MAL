package com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.Adapters.CharactersAdapter;
import com.omnicoder.anichan.Models.AnimeResponse.Characters.CharacterData;
import com.omnicoder.anichan.ViewModels.MangaDetailsViewModel;
import com.omnicoder.anichan.ViewModels.ViewAnimeViewModel;
import com.omnicoder.anichan.databinding.FragmentSeasonDetailsBinding;

import java.util.List;


public class CharactersFragment extends Fragment {
    FragmentSeasonDetailsBinding binding;
    ViewAnimeViewModel animeViewModel;
    MangaDetailsViewModel mangaViewModel;
    int id;


    public CharactersFragment(int id, MangaDetailsViewModel mangaViewModel) {
        this.mangaViewModel = mangaViewModel;
        this.id=id;
    }

    public CharactersFragment(int id, ViewAnimeViewModel animeViewModel) {
        this.animeViewModel = animeViewModel;
        this.id=id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= FragmentSeasonDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(animeViewModel==null){
            mangaViewModel.fetchCharacters(id);
            mangaViewModel.getCharacters().observe(getViewLifecycleOwner(), this::setCharacters);
        }else{
            animeViewModel.fetchCharacters(id);
            animeViewModel.getCharacters().observe(getViewLifecycleOwner(), this::setCharacters);
        }

    }

    private void setCharacters(List<CharacterData> characterData){
        CharactersAdapter adapter = new CharactersAdapter(getContext(),characterData);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.recyclerView.setAdapter(adapter);
    }




}