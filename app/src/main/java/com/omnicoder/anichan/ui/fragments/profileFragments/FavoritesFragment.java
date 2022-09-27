package com.omnicoder.anichan.ui.fragments.profileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.JikanEntityAdapter;
import com.omnicoder.anichan.adapters.SeasonAdapter;
import com.omnicoder.anichan.databinding.FragmentFavoritesBinding;
import com.omnicoder.anichan.models.jikan.Favorites;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.models.responses.Data;

import java.util.List;

public class FavoritesFragment extends Fragment {
    private static final int RECYCLER_VIEW_ITEM_COUNT=9;
    Favorites favorites;
    FragmentFavoritesBinding binding;

    public FavoritesFragment() {}

    public FavoritesFragment(Favorites favorites) {
        this.favorites=favorites;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentFavoritesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JikanEntityAdapter animeAdapter= new JikanEntityAdapter(getContext(),favorites.getAnime(),true,RECYCLER_VIEW_ITEM_COUNT);
        JikanEntityAdapter mangaAdapter= new JikanEntityAdapter(getContext(),favorites.getManga(),false,RECYCLER_VIEW_ITEM_COUNT);
        JikanEntityAdapter characterAdapter= new JikanEntityAdapter(getContext(),favorites.getCharacters(),false,RECYCLER_VIEW_ITEM_COUNT);
        JikanEntityAdapter peopleAdapter= new JikanEntityAdapter(getContext(),favorites.getPeople(),false,RECYCLER_VIEW_ITEM_COUNT);
        binding.animeView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.mangaView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.animeView.setAdapter(animeAdapter);
        binding.mangaView.setAdapter(mangaAdapter);



    }
}