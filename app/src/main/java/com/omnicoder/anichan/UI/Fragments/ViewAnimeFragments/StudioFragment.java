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

import com.omnicoder.anichan.Adapters.StudioAdapter;
import com.omnicoder.anichan.Models.ViewAnime;
import com.omnicoder.anichan.databinding.FragmentStudioBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class StudioFragment extends Fragment {
    ViewAnime viewAnime;
    FragmentStudioBinding binding;

    public StudioFragment(ViewAnime viewAnime) {
        this.viewAnime=viewAnime;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentStudioBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context= getContext();
        Single.fromCallable(() -> viewAnime.getAllProductionCompanies())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productionCompanies -> {
                    StudioAdapter adapter = new StudioAdapter(context,productionCompanies);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
                    binding.recyclerView.setAdapter(adapter);
                } ,e-> Log.d("Companyy"," "+e.getMessage()));
    }

}