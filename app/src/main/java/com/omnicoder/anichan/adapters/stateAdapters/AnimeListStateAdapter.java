package com.omnicoder.anichan.adapters.stateAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.ui.fragments.animeList.CompletedFragment;
import com.omnicoder.anichan.ui.fragments.animeList.DroppedFragment;
import com.omnicoder.anichan.ui.fragments.animeList.OnHoldFragment;
import com.omnicoder.anichan.ui.fragments.animeList.PlanToWatchFragment;
import com.omnicoder.anichan.ui.fragments.animeList.ReWatchingFragment;
import com.omnicoder.anichan.ui.fragments.animeList.WatchingFragment;

public class AnimeListStateAdapter extends FragmentStateAdapter {
    WatchingFragment watchingFragment;
    PlanToWatchFragment planToWatchFragment;
    CompletedFragment completedFragment;
    OnHoldFragment onHoldFragment;
    DroppedFragment droppedFragment;
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
                if(completedFragment==null){
                    completedFragment=CompletedFragment.newInstance();
                }
                return completedFragment;
            case 3:
                if(onHoldFragment==null){
                    onHoldFragment=OnHoldFragment.newInstance();
                }
                return onHoldFragment;
            case 4:
                if(droppedFragment==null){
                    droppedFragment=DroppedFragment.newInstance();
                }
                return droppedFragment;
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
