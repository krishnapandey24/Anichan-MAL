package com.omnicoder.anichan.UI.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.Adapters.SearchAnimeListAdapter;
import com.omnicoder.anichan.Adapters.SearchMangaListAdapter;
import com.omnicoder.anichan.ViewModels.AnimeListViewModel;
import com.omnicoder.anichan.ViewModels.MangaListViewModel;
import com.omnicoder.anichan.databinding.SearchListBottomSheetBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchListFragment extends Fragment {
    SearchListBottomSheetBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchListBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        boolean isAnimeList=getArguments().getBoolean("isAnimeList");
        binding.searchEditText.requestFocus();
        ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        binding.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return false;
        });
        if(isAnimeList){
            binding.searchEditText.setHint("Search Anime");
            AnimeListViewModel animeListViewModel = new ViewModelProvider(this).get(AnimeListViewModel.class);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            animeListViewModel.getSearchResults().observe(getViewLifecycleOwner(), animeLists -> binding.recyclerView.setAdapter(new SearchAnimeListAdapter(context, animeLists)));
            binding.searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    animeListViewModel.searchAnime(s);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else{
            binding.searchEditText.setHint("Search Manga");
            MangaListViewModel mangaListViewModel = new ViewModelProvider(this).get(MangaListViewModel.class);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            mangaListViewModel.getSearchResults().observe(getViewLifecycleOwner(), mangaList -> binding.recyclerView.setAdapter(new SearchMangaListAdapter(context, mangaList)));
            binding.searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mangaListViewModel.searchManga(s);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        binding.backButton.setOnClickListener(v -> requireActivity().onBackPressed());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;

    }
}
