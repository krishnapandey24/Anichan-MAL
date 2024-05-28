package com.omnicoder.anichan.adapters.stateAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.ui.fragments.mangaList.MangaListByStatusFragment;
import com.omnicoder.anichan.utils.Constants;
import com.omnicoder.anichan.viewModels.MangaListViewModel;

public class MangaListAdapter extends FragmentStateAdapter {
    MangaListByStatusFragment readingFragment;
    MangaListByStatusFragment planToReadFragment;
    MangaListByStatusFragment completedMangaFragment;
    MangaListByStatusFragment onHoldMangaFragment;
    MangaListByStatusFragment droppedMangaFragment;

    private final MangaListViewModel viewModel;



    public MangaListAdapter(@NonNull Fragment fragment, MangaListViewModel mangaListViewModel) {
        super(fragment);
        this.viewModel = mangaListViewModel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                if(readingFragment==null){
                    readingFragment=new MangaListByStatusFragment(Constants.READING, viewModel.reading);
                }
                return readingFragment;
            case 1:
                if(planToReadFragment==null){
                    planToReadFragment= new MangaListByStatusFragment(Constants.PLAN_TO_READ, viewModel.planToRead);
                }
                return planToReadFragment;
            case 2:
                if(completedMangaFragment ==null){
                    completedMangaFragment = new MangaListByStatusFragment(Constants.COMPLETED, viewModel.completed);
                }
                return completedMangaFragment;
            case 3:
                if(onHoldMangaFragment ==null){
                    onHoldMangaFragment = new MangaListByStatusFragment(Constants.ON_HOLD, viewModel.onHold);
                }
                return onHoldMangaFragment;
            case 4:
                if(droppedMangaFragment ==null){
                    droppedMangaFragment = new MangaListByStatusFragment(Constants.DROPPED, viewModel.dropped);
                }
                return droppedMangaFragment;
            default:
                return readingFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
