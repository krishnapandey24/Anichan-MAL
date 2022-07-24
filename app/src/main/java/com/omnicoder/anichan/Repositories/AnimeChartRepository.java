package com.omnicoder.anichan.Repositories;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Network.RxAPI;
import com.omnicoder.anichan.Paging.RankingPagingSource;
import com.omnicoder.anichan.Paging.SeasonPagingSource;
import com.omnicoder.anichan.Utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class AnimeChartRepository {
    RxAPI rxAPI;
    String accessToken;
    SharedPreferences sharedPreferences;
    boolean nsfw;

    @Inject
    public AnimeChartRepository(RxAPI rxAPI,Context context){
        this.rxAPI= rxAPI;
        SharedPreferences sharedPreferences=context.getSharedPreferences("AccessToken", Context.MODE_PRIVATE);
        this.accessToken=" Bearer "+sharedPreferences.getString("accessToken",null);
        this.nsfw=sharedPreferences.getBoolean("nsfw",false);
    }

    public Flowable<PagingData<Data>> getRanking(String rankingType){
        RankingPagingSource rankingPagingSource= new RankingPagingSource(rxAPI,rankingType,accessToken,nsfw);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> rankingPagingSource));
    }

    public Flowable<PagingData<Data>> getSeason(String year,String season){
        SeasonPagingSource seasonPagingSource= new SeasonPagingSource(rxAPI,accessToken,year,season,nsfw);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> seasonPagingSource));
    }


}
