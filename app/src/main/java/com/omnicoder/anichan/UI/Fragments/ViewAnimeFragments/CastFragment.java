package com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.Adapters.CastAdapter;
import com.omnicoder.anichan.Adapters.CrewAdapter;
import com.omnicoder.anichan.Models.ViewAnime;
import com.omnicoder.anichan.databinding.FragmentSeasonDetailsBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CastFragment extends Fragment {
    ViewAnime viewAnime;
    boolean b;
    FragmentSeasonDetailsBinding binding;


    public CastFragment(ViewAnime viewAnime,boolean b) {
        this.viewAnime=viewAnime;
        this.b=b;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentSeasonDetailsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context=getContext();
        if(b){
            Single.fromCallable(()->viewAnime.getCast())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(castList->{
                        CastAdapter adapter = new CastAdapter(context,viewAnime.getCast());
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                        binding.recyclerView.setAdapter(adapter);
                    },e-> Log.d("tagg","Error "+e.getMessage()));
        }else {
            Single.fromCallable(()->viewAnime.getCrew())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(crewList->{
                        CrewAdapter adapter = new CrewAdapter(context,crewList);
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                        binding.recyclerView.setAdapter(adapter);
                    },e-> Log.d("tagg","Error "+e.getMessage()));
        }

    }




}