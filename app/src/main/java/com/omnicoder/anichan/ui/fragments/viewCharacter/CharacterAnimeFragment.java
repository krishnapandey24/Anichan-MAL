package com.omnicoder.anichan.ui.fragments.viewCharacter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.CharacterAnimeAdapter;
import com.omnicoder.anichan.adapters.recyclerViews.VoiceActorsAdapter;
import com.omnicoder.anichan.models.jikan.CharacterAnime;
import com.omnicoder.anichan.models.jikan.CharacterVoiceActor;

import java.util.List;


public class CharacterAnimeFragment extends Fragment {
    List<CharacterAnime> anime;

    public CharacterAnimeFragment(List<CharacterAnime> anime) {
        this.anime = anime;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voice_actors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CharacterAnimeAdapter characterAnimeAdapter=new CharacterAnimeAdapter(getContext(),anime);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(characterAnimeAdapter);
    }
}