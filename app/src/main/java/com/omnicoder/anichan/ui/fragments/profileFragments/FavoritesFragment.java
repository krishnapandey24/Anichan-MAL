package com.omnicoder.anichan.ui.fragments.profileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.FragmentFavoritesBinding;
import com.omnicoder.anichan.models.jikan.Favorites;

public class FavoritesFragment extends Fragment {
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
    }
}