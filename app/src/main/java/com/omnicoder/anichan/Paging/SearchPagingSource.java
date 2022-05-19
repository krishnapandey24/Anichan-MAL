package com.omnicoder.anichan.Paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.omnicoder.anichan.Models.Animes;
import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Models.Responses.RankingResponse;
import com.omnicoder.anichan.Network.RxAPI;
import com.omnicoder.anichan.Utils.Constants;
import com.omnicoder.anichan.Utils.SearchComparator;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPagingSource extends RxPagingSource<Integer, Data> {
    private final RxAPI rxAPI;
    private final String query;
    private final String accessToken;


    public SearchPagingSource(RxAPI rxAPI, String query, String accessToken){
        this.rxAPI=rxAPI;
        this.query = query;
        this.accessToken=accessToken;
    }



    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Data> pagingState) {
        return null;
    }



    @NonNull
    @Override
    public Single<LoadResult<Integer, Data>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        int offset= loadParams.getKey() != null ? loadParams.getKey() : Constants.OFFSET;
        int limit= Constants.SEARCH_LIMIT;
            return rxAPI.searchAnime(accessToken,query,limit,offset)
                    .subscribeOn(Schedulers.io())
                    .flattenAsObservable(searchResponse -> {
                        List<Data> results=searchResponse.getData();
                        Collections.sort(results,new SearchComparator());
                        return results;
                    })
                    .toList()
                    .map(rankingResponse -> toLoadResult(rankingResponse,offset,limit))
                    .onErrorReturn(LoadResult.Error::new);

    }
    public LoadResult<Integer, Data> toLoadResult(List<Data> results, int offset, int limit){
        int nextOffset= results.isEmpty() ? null : offset + limit;
        return new LoadResult.Page(results, offset == Constants.OFFSET ? null : offset - limit,nextOffset);
    }

}
