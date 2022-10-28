package com.omnicoder.anichan.ui.fragments;

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
import com.omnicoder.anichan.adapters.recyclerViews.JikanEntityAdapter;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;

import java.util.List;


public class JikanSubEntityFragment extends Fragment {
    List<JikanSubEntity> entities;
    String entityType;


    public JikanSubEntityFragment(List<JikanSubEntity> entities, String entityType){
        this.entities=entities;
        this.entityType=entityType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jikan_sub_entity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JikanEntityAdapter jikanEntityAdapter=new JikanEntityAdapter(getContext(),entities,entityType,entities.size());
        RecyclerView recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(jikanEntityAdapter);
    }
}