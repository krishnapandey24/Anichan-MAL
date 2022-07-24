package com.omnicoder.anichan.Paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Models.Responses.RankingResponse;
import com.omnicoder.anichan.Network.RxAPI;
import com.omnicoder.anichan.Utils.Constants;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RankingPagingSource extends RxPagingSource<Integer, Data> {
    private final RxAPI rxAPI;
    private final String rankingType;
    private final String accessToken;
    private final boolean nsfw;


    public RankingPagingSource(RxAPI rxAPI, String rankingType, String accessToken, boolean nsfw){
        this.rxAPI=rxAPI;
        this.rankingType=rankingType;
        this.accessToken=accessToken;
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
        return rxAPI.getRanking(accessToken,rankingType, Constants.LIMIT,Constants.RANKING_FIELDS,nsfw,offset)
                .subscribeOn(Schedulers.io())
                .map(rankingResponse -> toLoadResult(rankingResponse,offset,limit))
                .onErrorReturn(LoadResult.Error::new);
    }

    public LoadResult<Integer, Data> toLoadResult(RankingResponse response,int offset, int limit){
        int nextOffset= response.getData()==null ? null : offset + limit;
        Log.d("tagg","offset got"+offset+" next of "+nextOffset);
        return new LoadResult.Page(response.getData(), offset == Constants.OFFSET ? null : offset - limit,nextOffset);
    }


}
