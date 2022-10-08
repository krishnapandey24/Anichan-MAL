package com.omnicoder.anichan.ui.fragments.profileFragments;

import static com.omnicoder.anichan.utils.Constants.ANIME;
import static com.omnicoder.anichan.utils.Constants.CHARACTERS;
import static com.omnicoder.anichan.utils.Constants.MANGA;
import static com.omnicoder.anichan.utils.Constants.PEOPLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.adapters.recyclerViews.JikanEntityAdapter;
import com.omnicoder.anichan.databinding.FragmentViewFavoritesBinding;
import com.omnicoder.anichan.models.jikan.Favorites;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;

import java.util.ArrayList;
import java.util.List;


public class ViewFavoritesFragment extends Fragment {
    FragmentViewFavoritesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentViewFavoritesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments()!=null){
            ViewFavoritesFragmentArgs args= ViewFavoritesFragmentArgs.fromBundle(getArguments());
            Favorites favorites= args.getFavorites();
            String favoritesType=args.getFavoritesType();
            List<JikanSubEntity> data=new ArrayList<>();
            switch (favoritesType){
                case ANIME:
                    data=favorites.getAnime();
                    break;
                case MANGA:
                    data=favorites.getManga();
                    break;
                case CHARACTERS:
                    data=favorites.getCharacters();
                    break;
                case PEOPLE:
                    data=favorites.getPeople();
                    break;
            }
            JikanEntityAdapter animeAdapter= new JikanEntityAdapter(getContext(),data,favoritesType,data.size());
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
            binding.recyclerView.setAdapter(animeAdapter);
            binding.toolbar.setTitle("Favorite "+favoritesType);
            binding.toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(v).popBackStack());
            }
        }
    }
