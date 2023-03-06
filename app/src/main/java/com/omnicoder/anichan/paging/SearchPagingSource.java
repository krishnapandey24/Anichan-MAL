package com.omnicoder.anichan.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.utils.Constants;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPagingSource extends RxPagingSource<Integer, Data> {
    private final MalApi malApi;
    private final String query;
    private final boolean nsfw;
    private final int isAnime;
    private static final String ANIME= "anime";
    private static final String MANGA="manga";
    private final String fields;
    private final String animeOrManga;


    public SearchPagingSource(MalApi malApi, String query, boolean nsfw, int isAnime,String fields, String animeOrManga){
        this.malApi=malApi;
        this.query = query;
        this.nsfw = nsfw;
        this.isAnime=isAnime;
        this.fields=fields;
        this.animeOrManga=animeOrManga;
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
            return malApi.searchAnime(animeOrManga,query,limit,nsfw,offset,fields)
                    .subscribeOn(Schedulers.io())
                    .map(rankingResponse -> {
                        List<Data> response=rankingResponse.getData();
                        response.get(0).setAnime(isAnime);
                        return toLoadResult(response,offset,limit);
                    })
                    .onErrorReturn(LoadResult.Error::new);

    }
    public LoadResult<Integer, Data> toLoadResult(List<Data> results, int offset, int limit){
        int nextOffset= results.isEmpty() ? null : offset + limit;
        return new LoadResult.Page(results, offset == Constants.OFFSET ? null : offset - limit,nextOffset);
    }

}
