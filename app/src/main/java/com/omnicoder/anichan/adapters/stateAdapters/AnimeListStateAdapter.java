package com.omnicoder.anichan.adapters.stateAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.ui.fragments.animeList.CompletedAnimeFragment;
import com.omnicoder.anichan.ui.fragments.animeList.DroppedAnimeFragment;
import com.omnicoder.anichan.ui.fragments.animeList.OnHoldAnimeFragment;
import com.omnicoder.anichan.ui.fragments.animeList.PlanToWatchFragment;
import com.omnicoder.anichan.ui.fragments.animeList.ReWatchingFragment;
import com.omnicoder.anichan.ui.fragments.animeList.WatchingFragment;

public class AnimeListStateAdapter extends FragmentStateAdapter {
    WatchingFragment watchingFragment;
    PlanToWatchFragment planToWatchFragment;
    CompletedAnimeFragment completedAnimeFragment;
    OnHoldAnimeFragment onHoldAnimeFragment;
    DroppedAnimeFragment droppedAnimeFragment;
    ReWatchingFragment reWatchingFragment;



    public AnimeListStateAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                if(watchingFragment==null){
                    watchingFragment=WatchingFragment.newInstance();
                }
                return watchingFragment;
            case 1:
                if(planToWatchFragment==null){
                    planToWatchFragment=PlanToWatchFragment.newInstance();
                }
                return planToWatchFragment;
            case 2:
                if(completedAnimeFragment ==null){
                    completedAnimeFragment = CompletedAnimeFragment.newInstance();
                }
                return completedAnimeFragment;
            case 3:
                if(onHoldAnimeFragment ==null){
                    onHoldAnimeFragment = OnHoldAnimeFragment.newInstance();
                }
                return onHoldAnimeFragment;
            case 4:
                if(droppedAnimeFragment ==null){
                    droppedAnimeFragment = DroppedAnimeFragment.newInstance();
                }
                return droppedAnimeFragment;
            case 5:
                if(reWatchingFragment==null){
                    reWatchingFragment=ReWatchingFragment.newInstance();
                }
                return reWatchingFragment;
            default:
                return watchingFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
