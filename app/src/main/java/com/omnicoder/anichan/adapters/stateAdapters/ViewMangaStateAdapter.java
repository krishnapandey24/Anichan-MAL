package com.omnicoder.anichan.adapters.stateAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.models.mangaResponse.Manga;
import com.omnicoder.anichan.ui.fragments.viewAnime.CharactersFragment;
import com.omnicoder.anichan.ui.fragments.viewAnime.MangaSummaryFragment;
import com.omnicoder.anichan.ui.fragments.viewAnime.ReviewsFragment;
import com.omnicoder.anichan.viewModels.MangaDetailsViewModel;


public class ViewMangaStateAdapter extends FragmentStateAdapter {
    CharactersFragment charactersFragment;
    MangaSummaryFragment mangaSummaryFragment;
    ReviewsFragment reviewsFragment;
    int mangaId;
    MangaDetailsViewModel viewModel;
    Manga manga;

    public ViewMangaStateAdapter(@NonNull FragmentActivity fragmentActivity, MangaDetailsViewModel viewModel, Manga manga) {
        super(fragmentActivity);
        this.mangaId = manga.getId();
        this.viewModel = viewModel;
        this.manga = manga;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if (mangaSummaryFragment == null) {
                    mangaSummaryFragment = new MangaSummaryFragment(manga);
                }
                return mangaSummaryFragment;
            case 1:
                if (charactersFragment == null) {
                    charactersFragment = new CharactersFragment(mangaId, viewModel);
                }
                return charactersFragment;
            case 2:
                if (reviewsFragment == null) {
                    reviewsFragment = new ReviewsFragment(mangaId);
                }
                return reviewsFragment;
            default:
                return mangaSummaryFragment;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
