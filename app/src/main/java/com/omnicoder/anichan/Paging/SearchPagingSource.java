package com.omnicoder.anichan.Paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.omnicoder.anichan.Models.Animes;
import com.omnicoder.anichan.Network.MovieDB;
import com.omnicoder.anichan.Utils.Constants;
import com.omnicoder.anichan.Utils.SearchComparator;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPagingSource extends RxPagingSource<Integer, Animes> {
    private final MovieDB movieDB;
    private String searchQuery;
    boolean zeroResults=false;


    @Inject
    public SearchPagingSource(MovieDB movieDB){
        this.movieDB=movieDB;
    }


    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Animes> pagingState) {
        return null;
    }



    @NonNull
    @Override
    public Single<LoadResult<Integer, Animes>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        int page=loadParams.getKey() != null ? loadParams.getKey() : 1;
        try {
            return movieDB.searchAnime(searchQuery,page, Constants.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .flattenAsObservable(searchResponse -> {
                        List<Animes> results=searchResponse.getResults();
                        Collections.sort(results,new SearchComparator());
                        return results;
                    })
                    .filter(anime -> anime.getOriginal_language().equals("ja") && anime.getGenre_ids().contains(16))
                    .toList()
                    .map(anime -> {
                        if(anime.size()<1){
                            zeroResults=true;
                            return null;
                        }
                        return toLoadResult(anime,page);
                    })
                    .onErrorReturn(LoadResult.Error::new);
        }catch (Exception e){
            Log.d("MyNew","Error: "+e.getMessage());
            return Single.just(new LoadResult.Error<>(e));
        }

    }
    private LoadResult<Integer, Animes> toLoadResult(List<Animes> movies, int page) {
        return new LoadResult.Page(movies, page == 1 ? null : page - 1, zeroResults ? null :page+1);
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }


}
