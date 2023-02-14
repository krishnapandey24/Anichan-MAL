package com.omnicoder.anichan.adapters.stateAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.ui.fragments.mangaList.CompletedMangaFragment;
import com.omnicoder.anichan.ui.fragments.mangaList.DroppedMangaFragment;
import com.omnicoder.anichan.ui.fragments.mangaList.OnHoldMangaFragment;
import com.omnicoder.anichan.ui.fragments.mangaList.PlanToReadFragment;
import com.omnicoder.anichan.ui.fragments.mangaList.ReReadingFragment;
import com.omnicoder.anichan.ui.fragments.mangaList.ReadingFragment;

public class MangaListAdapter extends FragmentStateAdapter {
    ReadingFragment readingFragment;
    PlanToReadFragment planToReadFragment;
    CompletedMangaFragment completedMangaFragment;
    OnHoldMangaFragment onHoldMangaFragment;
    DroppedMangaFragment droppedMangaFragment;
    ReReadingFragment reReadingFragment;



    public MangaListAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                if(readingFragment==null){
                    readingFragment=ReadingFragment.newInstance();
                }
                return readingFragment;
            case 1:
                if(planToReadFragment==null){
                    planToReadFragment=PlanToReadFragment.newInstance();
                }
                return planToReadFragment;
            case 2:
                if(completedMangaFragment ==null){
                    completedMangaFragment = CompletedMangaFragment.newInstance();
                }
                return completedMangaFragment;
            case 3:
                if(onHoldMangaFragment ==null){
                    onHoldMangaFragment = OnHoldMangaFragment.newInstance();
                }
                return onHoldMangaFragment;
            case 4:
                if(droppedMangaFragment ==null){
                    droppedMangaFragment = DroppedMangaFragment.newInstance();
                }
                return droppedMangaFragment;
            case 5:
                if(reReadingFragment==null){
                    reReadingFragment=ReReadingFragment.newInstance();
                }
                return reReadingFragment;
            default:
                return readingFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
