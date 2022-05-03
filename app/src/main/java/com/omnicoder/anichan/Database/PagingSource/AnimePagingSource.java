package com.omnicoder.anichan.Database.PagingSource;

import android.util.Log;

import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.omnicoder.anichan.Database.AnimeDao;
import com.omnicoder.anichan.Models.ExploreView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnimePagingSource extends RxPagingSource<Integer, ExploreView> {
    private final AnimeDao animeDao;
    private String animeType;

    private SupportSQLiteQuery query;


    @Inject
    public AnimePagingSource(AnimeDao animeDao){
        this.animeDao= animeDao;
    }


    @Override
    public @Nullable Integer getRefreshKey(@NotNull PagingState<Integer, ExploreView> pagingState) {
        return null;
    }


    @Override
    public @NotNull Single<LoadResult<Integer, ExploreView>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        int PAGE_INDEX = 1;
        int page=loadParams.getKey() != null ? loadParams.getKey() : PAGE_INDEX;
        try{
            switch (animeType){
                case "Personalized":
                    return animeDao.getPersonalizedAnime(query)
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);
                case "Trending":
                    return animeDao.getTrendingAnime()
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);
                case "Most Popular":
                    return animeDao.getPopularAnime()
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);
                case "Highest Rated":
                    return animeDao.getTop100Anime()
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);
                case "Top Upcoming":
                    return animeDao.getTopUpcomingAnime()
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);


            }
            return animeDao.getTrendingAnime()
                    .subscribeOn(Schedulers.io())
                    .map(anime->toLoadResult(anime,page))
                    .onErrorReturn(LoadResult.Error::new);
        }catch (Exception e){
            return Single.just(new LoadResult.Error<>(e));

        }

    }

    private LoadResult<Integer, ExploreView> toLoadResult(List<ExploreView> movies, int page) {
        return new LoadResult.Page(movies, page == 1 ? null : page - 1, page + 1);
    }

    public void setAnimeType(String sortType) {
        this.animeType = sortType;
    }

    public void setQuery(SupportSQLiteQuery query){
        this.animeType="Personalized";
        this.query=query;
    }
}
