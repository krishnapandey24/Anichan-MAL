package com.omnicoder.anichan.repositories;

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
    String accessToken;
    boolean nsfw;

    @Inject
    public MangaRankingRepository(MalApi malApi, Context context){
        this.malApi= malApi;
        SharedPreferences sharedPreferences=context.getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
        this.accessToken=" Bearer "+sharedPreferences.getString("accessToken",null);
        this.nsfw=sharedPreferences.getBoolean("nsfw",false);
    }

    public Flowable<PagingData<Data>> getRanking(String rankingType){
        MangaRankingPagingSource rankingPagingSource= new MangaRankingPagingSource(malApi,rankingType,accessToken,nsfw);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> rankingPagingSource));
    }



}
