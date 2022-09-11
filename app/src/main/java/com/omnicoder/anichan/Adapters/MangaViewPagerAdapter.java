package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.Database.UserManga;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.UpdateAnimeBottomSheet;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.UpdateMangaBottomSheet;
import com.omnicoder.anichan.ViewModels.MangaListViewModel;
import com.omnicoder.anichan.databinding.FragmentTabBinding;


public class MangaViewPagerAdapter extends RecyclerView.Adapter<MangaViewPagerAdapter.PageHolder> implements UpdateMangaBottomSheet.UpdateManga, MangaListAdapter.MyViewHolder.UpdateMangaList {
    Context context;
    String[] tabs;
    FragmentTabBinding binding;
    MangaListViewModel viewModel;
    LifecycleOwner lifecycleOwner;
    RecyclerView recyclerView;
    MangaPagerAdapterInterface adapterInterface;
    String sortBy;
    boolean updateList =true;

    public MangaViewPagerAdapter(Context context, String[] tabs, MangaListViewModel viewModel, LifecycleOwner lifecycleOwner, MangaPagerAdapterInterface adapterInterface, String sortBy){
        this.context=context;
        this.tabs=tabs;
        this.viewModel=viewModel;
        this.lifecycleOwner=lifecycleOwner;
        this.adapterInterface = adapterInterface;
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
        viewModel.getMangaList(position,sortBy).observe(lifecycleOwner, mangaList-> {
            if(updateList) {
                recyclerView = holder.binding.recyclerView;
                MangaListAdapter adapter = new MangaListAdapter(context, mangaList, MangaViewPagerAdapter.this, MangaViewPagerAdapter.this);
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
    public void addChapter(int id, int noOfChaptersRead) {
        updateList =false;
        adapterInterface.addChapter(id,noOfChaptersRead);
    }

    @Override
    public void showEditor(UpdateMangaBottomSheet updateMangaBottomSheet) {
        adapterInterface.showEditor(updateMangaBottomSheet);

    }

    @Override
    public void mangaComplete(int id, String title) {
        updateList =true;
        adapterInterface.mangaCompleted(id,title);
    }

    @Override
    public void updateManga(UserManga manga, int position) {
        adapterInterface.updateManga(manga,position);
    }

    @Override
    public void deleteManga(int id) {
        updateList=true;
        adapterInterface.deleteManga(id);

    }


    public interface MangaPagerAdapterInterface {
        void updateManga(UserManga userManga, int position);
        void addChapter(int id,int noOfChaptersRead);
        void showEditor(UpdateMangaBottomSheet updateMangaBottomSheet);
        void mangaCompleted(int id,String name);
        void deleteManga(int id);
    }

    public static class PageHolder extends RecyclerView.ViewHolder{
        FragmentTabBinding binding;

        public PageHolder(@NonNull FragmentTabBinding fragmentTabBinding) {
            super(fragmentTabBinding.getRoot());
            this.binding=fragmentTabBinding;
        }
    }
}
