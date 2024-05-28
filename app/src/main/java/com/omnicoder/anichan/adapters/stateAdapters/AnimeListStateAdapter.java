package com.omnicoder.anichan.adapters.stateAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.ui.fragments.animeList.AnimeListStatusFragment;
import com.omnicoder.anichan.utils.Constants;
import com.omnicoder.anichan.viewModels.AnimeListViewModel;

public class AnimeListStateAdapter extends FragmentStateAdapter {
    AnimeListStatusFragment watchingFragment;
    AnimeListStatusFragment planToWatchFragment;
    AnimeListStatusFragment completedAnimeFragment;
    AnimeListStatusFragment onHoldAnimeFragment;
    AnimeListStatusFragment droppedAnimeFragment;

    AnimeListViewModel animeListViewModel;



    public AnimeListStateAdapter(@NonNull Fragment fragment, AnimeListViewModel animeListViewModel) {
        super(fragment);
        this.animeListViewModel = animeListViewModel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                if(watchingFragment==null){
                    watchingFragment=new AnimeListStatusFragment(Constants.WATCHING, animeListViewModel.watching);
                }
                return watchingFragment;
            case 1:
                if(planToWatchFragment==null){
                    planToWatchFragment=new AnimeListStatusFragment(Constants.PLAN_TO_WATCH, animeListViewModel.planToWatch);
                }
                return planToWatchFragment;
            case 2:
                if(completedAnimeFragment ==null){
                    completedAnimeFragment = new AnimeListStatusFragment(Constants.COMPLETED, animeListViewModel.completed);
                }
                return completedAnimeFragment;
            case 3:
                if(onHoldAnimeFragment ==null){
                    onHoldAnimeFragment = new AnimeListStatusFragment(Constants.ON_HOLD, animeListViewModel.onHold);
                }
                return onHoldAnimeFragment;
            case 4:
                if(droppedAnimeFragment ==null){
                    droppedAnimeFragment = new AnimeListStatusFragment(Constants.DROPPED, animeListViewModel.dropped);
                }
                return droppedAnimeFragment;
            default:
                return watchingFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
