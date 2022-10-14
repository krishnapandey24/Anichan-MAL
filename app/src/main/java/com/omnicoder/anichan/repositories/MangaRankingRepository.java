package com.omnicoder.anichan.repositories;

import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.paging.MangaRankingPagingSource;
import com.omnicoder.anichan.utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class MangaRankingRepository {
    MalApi malApi;
    boolean nsfw;


    @Inject
    public MangaRankingRepository(MalApi malApi, SharedPreferences sharedPreferences){
        this.malApi= malApi;
        this.nsfw=sharedPreferences.getBoolean(NSFW_TAG,false);
    }

    public Flowable<PagingData<Data>> getRanking(String rankingType){
        MangaRankingPagingSource rankingPagingSource= new MangaRankingPagingSource(malApi,rankingType,nsfw);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> rankingPagingSource));
    }



}
