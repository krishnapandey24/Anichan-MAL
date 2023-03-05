package com.omnicoder.anichan.ui.fragments.profile;

import static com.omnicoder.anichan.utils.Constants.ANIME;
import static com.omnicoder.anichan.utils.Constants.CHARACTERS;
import static com.omnicoder.anichan.utils.Constants.MANGA;
import static com.omnicoder.anichan.utils.Constants.PEOPLE;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.NavGraphDirections;
import com.omnicoder.anichan.adapters.recyclerViews.JikanEntityAdapter;
import com.omnicoder.anichan.databinding.FragmentFavoritesBinding;
import com.omnicoder.anichan.models.jikan.Favorites;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;

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
        if(favorites==null) return;
        Context context=getContext();
        List<JikanSubEntity> anime=favorites.getAnime();
        List<JikanSubEntity> manga=favorites.getManga();
        List<JikanSubEntity> characters=favorites.getCharacters();
        List<JikanSubEntity> people=favorites.getPeople();
        if(anime.size()>0){
            JikanEntityAdapter animeAdapter= new JikanEntityAdapter(context,anime,ANIME,Math.min(anime.size(), RECYCLER_VIEW_ITEM_COUNT) );
            binding.animeView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            binding.animeView.setAdapter(animeAdapter);
            binding.anime.setOnClickListener(v -> Navigation.findNavController(view).navigate(NavGraphDirections.moveToFavoritesFragment2(favorites, ANIME)));
        }else{
            binding.anime.setVisibility(View.GONE);
            binding.animeView.setVisibility(View.GONE);
        }
        
        if(manga.size()>0){
            JikanEntityAdapter mangaAdapter= new JikanEntityAdapter(context,manga,MANGA,Math.min(manga.size(), RECYCLER_VIEW_ITEM_COUNT));
            binding.mangaView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            binding.mangaView.setAdapter(mangaAdapter);
            binding.manga.setOnClickListener(v -> Navigation.findNavController(view).navigate(NavGraphDirections.moveToFavoritesFragment2(favorites, MANGA)));
        }else{
            binding.mangaView.setVisibility(View.GONE);
            binding.manga.setVisibility(View.GONE);
        }

        if(characters.size()>0){
            JikanEntityAdapter characterAdapter= new JikanEntityAdapter(context,characters,CHARACTERS,Math.min(characters.size(), RECYCLER_VIEW_ITEM_COUNT));
            binding.charactersView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            binding.charactersView.setAdapter(characterAdapter);
            binding.characters.setOnClickListener(v -> Navigation.findNavController(view).navigate(NavGraphDirections.moveToFavoritesFragment2(favorites, CHARACTERS)));
        }else{
            binding.charactersView.setVisibility(View.GONE);
            binding.characters.setVisibility(View.GONE);
        }
        
        if(people.size()>0){
            JikanEntityAdapter peopleAdapter= new JikanEntityAdapter(context,people,PEOPLE,Math.min(people.size(), RECYCLER_VIEW_ITEM_COUNT));
            binding.peopleView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            binding.peopleView.setAdapter(peopleAdapter);
            binding.people.setOnClickListener(v -> Navigation.findNavController(view).navigate(NavGraphDirections.moveToFavoritesFragment2(favorites, PEOPLE)));
        }else{
            binding.peopleView.setVisibility(View.GONE);
            binding.people.setVisibility(View.GONE);
        }

    }



}