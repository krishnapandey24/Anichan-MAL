package com.omnicoder.anichan.ui.fragments.profileFragments;

import static com.omnicoder.anichan.utils.Constants.ANIME;
import static com.omnicoder.anichan.utils.Constants.CHARACTERS;
import static com.omnicoder.anichan.utils.Constants.MANGA;
import static com.omnicoder.anichan.utils.Constants.PEOPLE;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.NavGraphDirections;
import com.omnicoder.anichan.adapters.JikanEntityAdapter;
import com.omnicoder.anichan.databinding.FragmentFavoritesBinding;
import com.omnicoder.anichan.models.jikan.Favorites;
import com.omnicoder.anichan.utils.Constants;

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
        Context context=getContext();
        Log.d("tagg","favorites: "+(favorites.getAnime().size()));
        JikanEntityAdapter animeAdapter= new JikanEntityAdapter(context,favorites.getAnime(),ANIME,Math.min(favorites.getAnime().size(), RECYCLER_VIEW_ITEM_COUNT) );
        JikanEntityAdapter mangaAdapter= new JikanEntityAdapter(context,favorites.getManga(),MANGA,Math.min(favorites.getManga().size(), RECYCLER_VIEW_ITEM_COUNT));
        JikanEntityAdapter characterAdapter= new JikanEntityAdapter(context,favorites.getCharacters(),CHARACTERS,Math.min(favorites.getCharacters().size(), RECYCLER_VIEW_ITEM_COUNT));
        JikanEntityAdapter peopleAdapter= new JikanEntityAdapter(context,favorites.getPeople(),PEOPLE,Math.min(favorites.getPeople().size(), RECYCLER_VIEW_ITEM_COUNT));
        binding.animeView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.mangaView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.charactersView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.peopleView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.animeView.setAdapter(animeAdapter);
        binding.mangaView.setAdapter(mangaAdapter);
        binding.charactersView.setAdapter(characterAdapter);
        binding.peopleView.setAdapter(peopleAdapter);
        binding.anime.setOnClickListener(v -> Navigation.findNavController(view).navigate((NavDirections)NavGraphDirections.moveToFavoritesFragment(favorites, ANIME)));
        binding.manga.setOnClickListener(v -> Navigation.findNavController(view).navigate((NavDirections)NavGraphDirections.moveToFavoritesFragment(favorites, MANGA)));
        binding.characters.setOnClickListener(v -> Navigation.findNavController(view).navigate((NavDirections)NavGraphDirections.moveToFavoritesFragment(favorites, CHARACTERS)));
        binding.people.setOnClickListener(v -> Navigation.findNavController(view).navigate((NavDirections)NavGraphDirections.moveToFavoritesFragment(favorites, PEOPLE)));
    }
}