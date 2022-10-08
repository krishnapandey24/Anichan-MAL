package com.omnicoder.anichan.adapters.stateAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.ui.fragments.viewAnimeFragments.CharactersFragment;
import com.omnicoder.anichan.ui.fragments.viewAnimeFragments.ReviewsFragment;
import com.omnicoder.anichan.ui.fragments.viewAnimeFragments.StaffFragment;
import com.omnicoder.anichan.ui.fragments.viewAnimeFragments.AnimeSummaryFragment;
import com.omnicoder.anichan.viewModels.ViewAnimeViewModel;

public class ViewAnimeStateAdapter extends FragmentStateAdapter {
    CharactersFragment charactersFragment;
    AnimeSummaryFragment animeSummaryFragment;
    StaffFragment staffFragment;
    ReviewsFragment reviewsFragment;
    int animeId;
    ViewAnimeViewModel viewModel;
    Anime anime;

    public ViewAnimeStateAdapter(@NonNull FragmentActivity fragmentActivity, ViewAnimeViewModel viewModel, Anime anime) {
        super(fragmentActivity);
        this.animeId = anime.getId();
        this.viewModel = viewModel;
        this.anime = anime;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if (animeSummaryFragment == null) {
                    animeSummaryFragment = new AnimeSummaryFragment(anime, viewModel);
                }
                return animeSummaryFragment;
            case 1:
                if (charactersFragment == null) {
                    charactersFragment = new CharactersFragment(animeId, viewModel);
                }
                return charactersFragment;
            case 2:
                if (reviewsFragment == null) {
                    reviewsFragment = new ReviewsFragment(animeId);
                }
                return reviewsFragment;
            case 3:
                if (staffFragment == null) {
                    staffFragment = new StaffFragment(animeId, viewModel);
                }
                return staffFragment;
            default:
                return animeSummaryFragment;
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }


}
