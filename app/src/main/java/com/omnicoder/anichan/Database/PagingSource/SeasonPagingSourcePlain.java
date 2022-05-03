package com.omnicoder.anichan.Database.PagingSource;

import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.omnicoder.anichan.Database.AnimeDao;
import com.omnicoder.anichan.Models.ExplorePlainView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SeasonPagingSourcePlain extends RxPagingSource<Integer, ExplorePlainView> {
    private final AnimeDao animeDao;
    private String season;


    @Inject
    public SeasonPagingSourcePlain(AnimeDao animeDao){
        this.animeDao= animeDao;
    }


    @Override
    public @Nullable Integer getRefreshKey(@NotNull PagingState<Integer, ExplorePlainView> pagingState) {
        return null;
    }

    @Override
    public @NotNull Single<LoadResult<Integer, ExplorePlainView>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        int PAGE_INDEX = 1;
        int page=loadParams.getKey() != null ? loadParams.getKey() : PAGE_INDEX;
        try{
            return animeDao.getAnimeBySeasonPlain(season)
                    .subscribeOn(Schedulers.io())
                    .map(anime->toLoadResult(anime,page))
                    .onErrorReturn(LoadResult.Error::new);

        }catch (Exception e){
            return Single.just(new LoadResult.Error<>(e));
        }

    }

    private LoadResult<Integer, ExplorePlainView> toLoadResult(List<ExplorePlainView> anime, int page) {
        return new LoadResult.Page(anime, page == 1 ? null : page - 1, page + 1);
    }

    public void setSeason(String season) {
        this.season = season;

    }
}
