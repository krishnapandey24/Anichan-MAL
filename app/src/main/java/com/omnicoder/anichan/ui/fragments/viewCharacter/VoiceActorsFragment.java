package com.omnicoder.anichan.ui.fragments.viewCharacter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.VoiceActorsAdapter;
import com.omnicoder.anichan.models.jikan.CharacterVoiceActor;

import java.util.List;


public class VoiceActorsFragment extends Fragment {
    List<CharacterVoiceActor> voiceActors;

    public VoiceActorsFragment(List<CharacterVoiceActor> voiceActors) {
        this.voiceActors = voiceActors;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voice_actors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VoiceActorsAdapter voiceActorsAdapter=new VoiceActorsAdapter(getContext(),voiceActors);
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(voiceActorsAdapter);
    }
}