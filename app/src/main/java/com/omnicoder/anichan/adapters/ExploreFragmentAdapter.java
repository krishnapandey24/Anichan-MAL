package com.omnicoder.anichan.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.ui.fragments.ExploreAnimeFragment;
import com.omnicoder.anichan.ui.fragments.ExploreMangaFragment;
import com.omnicoder.anichan.ui.fragments.ScheduleFragment;

public class ExploreFragmentAdapter extends FragmentStateAdapter {
    ExploreAnimeFragment exploreAnimeFragment;
    ExploreMangaFragment exploreMangaFragment;
    ScheduleFragment scheduleFragment;

    public ExploreFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            if(exploreAnimeFragment==null){
                exploreAnimeFragment=new ExploreAnimeFragment();
            }
            return exploreAnimeFragment;
        }else if(position==1){
            if(exploreMangaFragment==null){
                exploreMangaFragment=new ExploreMangaFragment();
            }
            return exploreMangaFragment;
        }else{
            if(scheduleFragment==null){
                scheduleFragment=new ScheduleFragment();
            }
            return scheduleFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
