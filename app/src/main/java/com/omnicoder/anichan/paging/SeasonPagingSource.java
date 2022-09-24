package com.omnicoder.anichan.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.models.responses.RankingResponse;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.utils.Constants;

import java.util.Locale;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SeasonPagingSource extends RxPagingSource<Integer, Data> {
    private final MalApi malApi;
    private final String year,season,accessToken;
    private final boolean nsfw;


    public SeasonPagingSource(MalApi malApi, String accessToken, String year, String season, boolean nsfw){
        this.malApi=malApi;
        this.accessToken=accessToken;
        this.year=year;
        this.season=season.toLowerCase(Locale.ROOT);
        this.nsfw=nsfw;
    }


    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Data> pagingState) {
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Data>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        int offset =loadParams.getKey() != null ? loadParams.getKey() : Constants.OFFSET;
        int limit= Constants.LIMIT;
        return malApi.getSeason(accessToken,year,season,Constants.SEASON_SORT,Constants.LIMIT,Constants.RANKING_FIELDS,nsfw,offset)
                .subscribeOn(Schedulers.io())
                .map(rankingResponse -> toLoadResult(rankingResponse,offset,limit))
                .onErrorReturn(e->{
                    e.printStackTrace();
                    Log.d("tagg","message"+e.getMessage());
                    return new LoadResult.Error(e);
                });
    }

    public LoadResult<Integer, Data> toLoadResult(RankingResponse response,int offset, int limit){
        int nextOffset= response.getData()==null ? null : offset + limit;
        Log.d("tagg","offset got"+offset+" next of "+nextOffset);
        return new LoadResult.Page(response.getData(), offset == Constants.OFFSET ? null : offset - limit,nextOffset);
    }


}
