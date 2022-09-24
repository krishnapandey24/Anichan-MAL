package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.UpdateAnimeBottomSheet;
import com.omnicoder.anichan.ViewModels.AnimeListViewModel;
import com.omnicoder.anichan.databinding.FragmentTabBinding;


public class AnimeViewPagerAdapter extends RecyclerView.Adapter<AnimeViewPagerAdapter.PageHolder> implements UpdateAnimeBottomSheet.UpdateAnime, AnimeListAdapter.MyViewHolder.UpdateAnimeList {
    Context context;
    String[] tabs;
    FragmentTabBinding binding;
    AnimeListViewModel viewModel;
    LifecycleOwner lifecycleOwner;
    RecyclerView recyclerView;
    AnimePagerAdapterInterface animePagerAdapterInterface;
    String sortBy;
    boolean updateList =true;

    public AnimeViewPagerAdapter(Context context, String[] tabs, AnimeListViewModel viewModel, LifecycleOwner lifecycleOwner, AnimePagerAdapterInterface animePagerAdapterInterface, String sortBy){
        this.context=context;
        this.tabs=tabs;
        this.viewModel=viewModel;
        this.lifecycleOwner=lifecycleOwner;
        this.animePagerAdapterInterface = animePagerAdapterInterface;
        this.sortBy=sortBy;
    }

    @NonNull
    @Override
    public PageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= FragmentTabBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PageHolder holder, int position) {
        viewModel.getAnimeList(position,sortBy).observe(lifecycleOwner, animeList-> {
            if(updateList) {
                recyclerView = holder.binding.recyclerView;
                AnimeListAdapter adapter = new AnimeListAdapter(context, animeList, AnimeViewPagerAdapter.this, AnimeViewPagerAdapter.this,position);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }
        });
        updateList=true;
    }



    @Override
    public int getItemCount() {
        return 6;
    }



    @Override
    public void updateAnime(UserAnime userAnime, int position) {
        animePagerAdapterInterface.updateAnime(userAnime,position);
    }

    @Override
    public void deleteAnime(int id) {
        updateList=true;
        animePagerAdapterInterface.deleteAnime(id);

    }

    @Override
    public void addEpisode(int id,int noOfEpisodesWatched) {
        updateList =false;
        animePagerAdapterInterface.addEpisode(id,noOfEpisodesWatched);

    }

    @Override
    public void showEditor(UpdateAnimeBottomSheet updateAnimeBottomSheet) {
        animePagerAdapterInterface.showEditor(updateAnimeBottomSheet);

    }

    @Override
    public void animeComplete(int id, String name) {
        updateList =true;
        animePagerAdapterInterface.animeCompleted(id,name);

    }


    public interface AnimePagerAdapterInterface {
        void updateAnime(UserAnime userAnime, int position);
        void addEpisode(int id,int noOfEpisodesWatched);
        void showEditor(UpdateAnimeBottomSheet updateAnimeBottomSheet);
        void animeCompleted(int id,String name);
        void deleteAnime(int id);
    }

    public static class PageHolder extends RecyclerView.ViewHolder{
        FragmentTabBinding binding;

        public PageHolder(@NonNull FragmentTabBinding fragmentTabBinding) {
            super(fragmentTabBinding.getRoot());
            this.binding=fragmentTabBinding;
        }
    }
}
