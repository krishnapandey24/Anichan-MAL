package com.omnicoder.anichan.ui.fragments.viewAnime;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.adapters.recyclerViews.CharactersAdapter;
import com.omnicoder.anichan.databinding.FragmentSeasonDetailsBinding;
import com.omnicoder.anichan.models.animeResponse.Characters.CharacterData;
import com.omnicoder.anichan.viewModels.MangaDetailsViewModel;
import com.omnicoder.anichan.viewModels.ViewAnimeViewModel;

import java.util.List;


public class CharactersFragment extends Fragment {
    FragmentSeasonDetailsBinding binding;
    ViewAnimeViewModel animeViewModel;
    MangaDetailsViewModel mangaViewModel;
    int id;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(animeViewModel==null){
            mangaViewModel.fetchCharacters(id);
        }else{
            animeViewModel.fetchCharacters(id);
        }
    }

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
        Log.d("tagg","characetr fagment");
        if(animeViewModel==null){
            mangaViewModel.getCharacters().observe(getViewLifecycleOwner(), this::setCharacters);
        }else{
            animeViewModel.getCharacters().observe(getViewLifecycleOwner(), this::setCharacters);
        }

    }

    private void setCharacters(List<CharacterData> characterData){
        CharactersAdapter adapter = new CharactersAdapter(getContext(),characterData);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.recyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().requestLayout();
    }




}