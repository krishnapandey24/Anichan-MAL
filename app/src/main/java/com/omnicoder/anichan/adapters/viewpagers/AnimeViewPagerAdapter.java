package com.omnicoder.anichan.adapters.viewpagers;

import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.AnimeListAdapter;
import com.omnicoder.anichan.database.UserAnime;
import com.omnicoder.anichan.ui.fragments.bottomSheets.UpdateAnimeBottomSheet;
import com.omnicoder.anichan.viewModels.AnimeListViewModel;
import com.omnicoder.anichan.databinding.FragmentTabBinding;




public class AnimeViewPagerAdapter extends RecyclerView.Adapter<AnimeViewPagerAdapter.PageHolder> implements UpdateAnimeBottomSheet.UpdateAnime, AnimeListAdapter.MyViewHolder.UpdateAnimeList {
    Context context;
    String[] tabs;
    AnimeListViewModel viewModel;
    LifecycleOwner lifecycleOwner;
    RecyclerView recyclerView;
    AnimePagerAdapterInterface animePagerAdapterInterface;
    int sortBy;
    boolean updateList=true;

    public AnimeViewPagerAdapter(Context context, String[] tabs, AnimeListViewModel viewModel, LifecycleOwner lifecycleOwner, AnimePagerAdapterInterface animePagerAdapterInterface, int sortBy){
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
        return new PageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tab,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PageHolder holder, int position) {
        Log.d("tagg", "onBindViewHolder:  "+position);
        recyclerView = holder.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        viewModel.getAnimeList(position,sortBy).observe(lifecycleOwner, animeList-> {
            Log.d("tagg","size: "+animeList.size()+ "and "+animeList.get(0).getStatus());
            if(animeList.size()>0 && ((animeList.get(0).getStatus().equals(getWatchStatus(position)) || (animeList.get(0).isIs_rewatching() && getWatchStatus(position).equals("rewatching"))))){
//                if(updateList) {
                    AnimeListAdapter adapter = new AnimeListAdapter(context, animeList, AnimeViewPagerAdapter.this, AnimeViewPagerAdapter.this,position);
                    recyclerView.setAdapter(adapter);
//                }
            }
        });
//        updateList=true;
    }


    public static String getWatchStatus(int status) {
        switch (status) {
            case 0: return "watching";
            case 1: return "plan_to_watch";
            case 2: return "completed";
            case 3: return "on_hold";
            case 4: return "dropped";
            case 5: return "rewatching";
            default: return "Invalid input";
        }
    }



    @Override
    public int getItemCount() {
        return 6;
    }


    @Override
    public void updateAnime(UserAnime userAnime, int viewPagerPosition) {
        animePagerAdapterInterface.updateAnime(userAnime,viewPagerPosition);
        notifyItemChanged(viewPagerPosition);
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

    @Override
    public void fetchMore() {
        animePagerAdapterInterface.fetchMore();

    }


    public interface AnimePagerAdapterInterface {
        void updateAnime(UserAnime userAnime, int position);
        void addEpisode(int id,int noOfEpisodesWatched);
        void showEditor(UpdateAnimeBottomSheet updateAnimeBottomSheet);
        void animeCompleted(int id,String name);
        void deleteAnime(int id);
        void fetchMore();
    }

    public static class PageHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;


        public PageHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView=itemView.findViewById(R.id.recyclerView);
        }
    }
}
