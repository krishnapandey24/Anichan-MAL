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

import com.omnicoder.anichan.Adapters.CharactersAdapter;
import com.omnicoder.anichan.ViewModels.ViewAnimeViewModel;
import com.omnicoder.anichan.databinding.FragmentSeasonDetailsBinding;


public class CharactersFragment extends Fragment {
    FragmentSeasonDetailsBinding binding;
    ViewAnimeViewModel viewModel;
    int id;


    public CharactersFragment(int id, ViewAnimeViewModel viewModel) {
        this.viewModel=viewModel;
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
        viewModel.fetchCharacters(id);
        viewModel.getCharacters().observe(getViewLifecycleOwner(), characterData -> {
            CharactersAdapter adapter = new CharactersAdapter(getContext(),characterData);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            binding.recyclerView.setAdapter(adapter);
        });
    }




}