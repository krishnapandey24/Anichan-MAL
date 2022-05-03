package com.omnicoder.anichan.Database.PagingSource;

import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.omnicoder.anichan.Database.AnimeDao;
import com.omnicoder.anichan.Models.ExplorePlainView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnimePagingSourcePlain extends RxPagingSource<Integer, ExplorePlainView> {
    private final AnimeDao animeDao;
    private String animeType;

    private SupportSQLiteQuery query;


    @Inject
    public AnimePagingSourcePlain(AnimeDao animeDao){
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
            switch (animeType){
                case "Personalized":
                    return animeDao.getPersonalizedAnimePlain(query)
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);
                case "Trending":
                    return animeDao.getTrendingAnimePlain()
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);
                case "Most Popular":
                    return animeDao.getPopularAnimePlain()
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);
                case "Highest Rated":
                    return animeDao.getTop100Anime2()
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);
                case "Top Upcoming":
                    return animeDao.getTopUpcomingAnimePlain()
                            .subscribeOn(Schedulers.io())
                            .map(anime->toLoadResult(anime,page))
                            .onErrorReturn(LoadResult.Error::new);


            }
            return animeDao.getTrendingAnimePlain()
                    .subscribeOn(Schedulers.io())
                    .map(anime->toLoadResult(anime,page))
                    .onErrorReturn(LoadResult.Error::new);
        }catch (Exception e){
            return Single.just(new LoadResult.Error<>(e));

        }

    }

    private LoadResult<Integer, ExplorePlainView> toLoadResult(List<ExplorePlainView> movies, int page) {
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
