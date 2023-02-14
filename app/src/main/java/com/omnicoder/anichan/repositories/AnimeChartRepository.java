package com.omnicoder.anichan.repositories;

import static com.omnicoder.anichan.utils.Constants.ANIME_JAPANESE_TITLES;
import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.network.MalApi;
import com.omnicoder.anichan.paging.RankingPagingSource;
import com.omnicoder.anichan.paging.SeasonPagingSource;
import com.omnicoder.anichan.paging.SuggestedPagingSource;
import com.omnicoder.anichan.utils.Constants;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class AnimeChartRepository {
    MalApi malApi;
    boolean nsfw,japaneseTitles;


    @Inject
    public AnimeChartRepository(MalApi malApi, SharedPreferences sharedPreferences){
        this.malApi= malApi;
        this.nsfw=sharedPreferences.getBoolean(NSFW_TAG,false);
        this.japaneseTitles=sharedPreferences.getBoolean(ANIME_JAPANESE_TITLES,false);
    }

    public Flowable<PagingData<Data>> getRanking(String rankingType,RankingPagingSource.ErrorHandler errorHandler){
        String fields= japaneseTitles ? Constants.RANKING_FIELDS : Constants.RANKING_FIELDS+Constants.NUM_SCORE;
        RankingPagingSource rankingPagingSource= new RankingPagingSource(malApi,rankingType,nsfw,errorHandler,fields);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> rankingPagingSource));
    }

    public Flowable<PagingData<Data>> getSuggestedAnime(SuggestedPagingSource.ErrorHandler errorHandler){
        String fields= japaneseTitles ? Constants.RANKING_FIELDS : Constants.RANKING_FIELDS+Constants.NUM_SCORE;
        SuggestedPagingSource rankingPagingSource= new SuggestedPagingSource(malApi,nsfw,errorHandler,fields);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> rankingPagingSource));
    }


    public Flowable<PagingData<Data>> getSeason(String year, String season, SeasonPagingSource.ErrorHandler errorHandler){
        String fields= japaneseTitles ? Constants.RANKING_FIELDS : Constants.RANKING_FIELDS+Constants.NUM_SCORE;
        SeasonPagingSource seasonPagingSource= new SeasonPagingSource(malApi,year,season,nsfw,errorHandler,fields);
        return PagingRx.getFlowable(new Pager(new PagingConfig(Constants.LIMIT),() -> seasonPagingSource));
    }


}
