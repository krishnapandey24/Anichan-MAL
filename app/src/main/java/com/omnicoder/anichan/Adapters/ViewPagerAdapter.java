package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Database.Anime;
import com.omnicoder.anichan.Database.AnimeList;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.UpdateAnimeBottomSheet;
import com.omnicoder.anichan.ViewModels.AnimeListViewModel;
import com.omnicoder.anichan.databinding.FragmentTabBinding;


public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.PageHolder> implements UpdateAnimeBottomSheet.UpdateAnime, AnimeListAdapter.MyViewHolder.UpdateAnimeList {
    Context context;
    String[] tabs;
    FragmentTabBinding binding;
    AnimeListViewModel viewModel;
    LifecycleOwner lifecycleOwner;
    RecyclerView recyclerView;
    PagerAdapter pagerAdapter;
    boolean b=true;

    public      ViewPagerAdapter(Context context, String[] tabs, AnimeListViewModel viewModel, LifecycleOwner lifecycleOwner,PagerAdapter pagerAdapter){
        this.context=context;
        this.tabs=tabs;
        this.viewModel=viewModel;
        this.lifecycleOwner=lifecycleOwner;
        this.pagerAdapter=pagerAdapter;

    }

    @NonNull
    @Override
    public PageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= FragmentTabBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PageHolder holder, int position) {
        viewModel.getAnimeList(position).observe(lifecycleOwner, animeList-> {
            if(b) {
                recyclerView = holder.binding.recyclerView;
                AnimeListAdapter adapter = new AnimeListAdapter(context, animeList, ViewPagerAdapter.this,ViewPagerAdapter.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }
        });
        b=true;
    }



    @Override
    public int getItemCount() {
        return 6;
    }



    @Override
    public void updateAnime(Anime anime, int position) {

    }

    @Override
    public void deleteAnime(int id) {
        b=true;
        pagerAdapter.deleteAnime(id);

    }

    @Override
    public void addEpisode(int id) {
        b=false;
        pagerAdapter.addEpisode(id);

    }

    @Override
    public void showEditor(UpdateAnimeBottomSheet updateAnimeBottomSheet) {
        pagerAdapter.showEditor(updateAnimeBottomSheet);

    }

    @Override
    public void animeComplete(int id, String name) {
        b=true;
        pagerAdapter.animeCompleted(id,name);

    }


    public interface PagerAdapter{
        void updateAnime(AnimeList animeList, int position);
        void addEpisode(int id);
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
