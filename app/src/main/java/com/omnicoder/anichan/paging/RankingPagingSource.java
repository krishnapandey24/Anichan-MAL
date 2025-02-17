package com.omnicoder.anichan.paging;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.models.responses.RankingResponse;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.utils.Constants;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RankingPagingSource extends RxPagingSource<Integer, Data> {
    private final MalApi malApi;
    private final String rankingType;
    private final boolean nsfw;
    private final ErrorHandler errorHandler;
    private final String fields;


    public RankingPagingSource(MalApi malApi, String rankingType, boolean nsfw,ErrorHandler errorHandler, String fields){
        this.malApi=malApi;
        this.rankingType=rankingType;
        this.nsfw=nsfw;
        this.errorHandler=errorHandler;
        this.fields=fields;


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
        return malApi.getAnimeRanking(rankingType, Constants.LIMIT,fields,nsfw,offset)
                .subscribeOn(Schedulers.io())
                .map(rankingResponse -> toLoadResult(rankingResponse,offset,limit))
                .onErrorReturn(e->{
                    errorHandler.error();
                    e.printStackTrace();
                    return new LoadResult.Error(e);
                });
    }

    public LoadResult<Integer, Data> toLoadResult(RankingResponse response,int offset, int limit){
        int nextOffset= response.getData()==null ? null : offset + limit;
        return new LoadResult.Page(response.getData(), offset == Constants.OFFSET ? null : offset - limit,nextOffset);
    }


    public interface ErrorHandler{
        void error();
    }


}
