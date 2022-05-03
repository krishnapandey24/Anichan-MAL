package com.omnicoder.anichan.Repositories;


import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.omnicoder.anichan.Database.Anime;
import com.omnicoder.anichan.Database.AnimeDao;
import com.omnicoder.anichan.Database.PagingSource.AnimePagingSource;
import com.omnicoder.anichan.Database.PagingSource.AnimePagingSourcePlain;
import com.omnicoder.anichan.Database.PagingSource.SeasonPagingSource;
import com.omnicoder.anichan.Database.PagingSource.SeasonPagingSourcePlain;
import com.omnicoder.anichan.Models.AllAnime;
import com.omnicoder.anichan.Models.Animes;
import com.omnicoder.anichan.Models.ExplorePlainView;
import com.omnicoder.anichan.Models.ExploreView;
import com.omnicoder.anichan.Models.TrendingAnime;
import com.omnicoder.anichan.Network.RxAPI;
import com.omnicoder.anichan.Network.SearchPagingSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


public class ExploreRepository {
    AnimeDao animeDao;
    RxAPI rxAPI;
    AnimePagingSource animePagingSource;
    SeasonPagingSource seasonPagingSource;
    SearchPagingSource searchPagingSource;
    AnimePagingSourcePlain animePagingSourcePlain;
    SeasonPagingSourcePlain seasonPagingSourcePlain;

    @Inject
    public ExploreRepository(AnimeDao animeDao, RxAPI rxAPI, AnimePagingSource animePagingSource, SeasonPagingSource seasonPagingSource, SearchPagingSource searchPagingSource, AnimePagingSourcePlain animePagingSourcePlain, SeasonPagingSourcePlain seasonPagingSourcePlain){
        this.animeDao= animeDao;
        this.rxAPI= rxAPI;
        this.animePagingSource=animePagingSource;
        this.seasonPagingSource=seasonPagingSource;
        this.searchPagingSource=searchPagingSource;
        this.animePagingSourcePlain= animePagingSourcePlain;
        this.seasonPagingSourcePlain=seasonPagingSourcePlain;

    }

    public Flowable<List<TrendingAnime>> get9TrendingAnime(){
        return animeDao.get9TrendingAnime();
    }

    public Flowable<List<ExploreView>> get9PopularAnime(){
        return animeDao.get9PopularAnime();
    }

    public Flowable<List<ExploreView>> get9Top100Anime(){
        return animeDao.get9Top100Anime();
    }

    public Flowable<List<ExploreView>> get9TopUpcomingAnime(){
        return animeDao.get9TopUpcomingAnime();
    }

    public Observable<AllAnime> fetchAllAnime(){
        return rxAPI.getAllAnime();
    }


    public Completable insertAllAnime(List<Anime> animeList){
        return animeDao.insertAllAnime(animeList);
    }

    public Completable reset(){
        return animeDao.reset();
    }



    public io.reactivex.rxjava3.core.Flowable<PagingData<ExploreView>> getPageFlowable(String animeType){
        animePagingSource.setAnimeType(animeType);
        Pager<Integer,ExploreView> pager=new Pager( new PagingConfig(20,20,false,20,600),()-> animePagingSource);
        return PagingRx.getFlowable(pager);

    }
    public io.reactivex.rxjava3.core.Flowable<PagingData<ExplorePlainView>> getPageFlowablePlain(String animeType){
        animePagingSourcePlain.setAnimeType(animeType);
        Pager<Integer,ExplorePlainView> pager=new Pager( new PagingConfig(9,3,false,9,600),()-> animePagingSourcePlain);
        return PagingRx.getFlowable(pager);

    }

    public io.reactivex.rxjava3.core.Flowable<PagingData<ExploreView>> getSeasonFlowable(String season){
        seasonPagingSource.setSeason(season);
        Pager<Integer,ExploreView> pager=new Pager( new PagingConfig(9,3,false,9,100),()-> seasonPagingSource);
        return PagingRx.getFlowable(pager);
    }

    public io.reactivex.rxjava3.core.Flowable<PagingData<ExplorePlainView>> getSeasonFlowablePlain(String season){
        seasonPagingSourcePlain.setSeason(season);
        Pager<Integer,ExplorePlainView> pager=new Pager( new PagingConfig(9,3,false,9,100),()-> seasonPagingSourcePlain);
        return PagingRx.getFlowable(pager);
    }


    public Flowable<PagingData<ExploreView>> getPersonalizedAnime(String sortBy,String airingStatus,String format){
        String query;
        if(sortBy.equals("Release Date")){
            query="SELECT animeID,title,imageURL,mediaType FROM ANIME WHERE status='"+airingStatus+"' AND format='"+format+"' ORDER BY date(releaseDate) DESC";
        }
        else{
            query="SELECT animeID,title,imageURL,mediaType FROM ANIME WHERE status='"+airingStatus+"' AND format='"+format+"' ORDER BY "+sortBy+" DESC";

        }
        animePagingSource.setQuery(new SimpleSQLiteQuery(query));
        Pager<Integer,ExploreView> pager=new Pager( new PagingConfig(9,3,false,9,600),()-> animePagingSource);
        return PagingRx.getFlowable(pager);
    }

    public Flowable<PagingData<ExploreView>> getPersonalizedAnime(String sortBy,String format){
        String query;
        if(sortBy.equals("Release Date")){
            query="SELECT animeID,title,imageURL,mediaType FROM ANIME WHERE format='"+format+"' ORDER BY date(releaseDate) DESC";
        }
        else{
            query="SELECT animeID,title,imageURL,mediaType FROM ANIME WHERE format='"+format+"' ORDER BY "+sortBy+" DESC";
        }
        animePagingSource.setQuery(new SimpleSQLiteQuery(query));
        Pager<Integer,ExploreView> pager=new Pager( new PagingConfig(9,3,false,9,600),()-> animePagingSource);
        return PagingRx.getFlowable(pager);
    }


    public Flowable<PagingData<ExplorePlainView>> getPersonalizedAnimePlain(String sortBy,String airingStatus,String format){
        String query;
        if(sortBy.equals("Release Date")){
            query="SELECT animeID,title,imageURL,mediaType,rating,genres FROM ANIME WHERE status='"+airingStatus+"' AND format='"+format+"' ORDER BY date(releaseDate) DESC";
        }
        else{
            query="SELECT animeID,title,imageURL,mediaType,rating,genres FROM ANIME WHERE status='"+airingStatus+"' AND format='"+format+"' ORDER BY "+sortBy+" DESC";

        }
        animePagingSourcePlain.setQuery(new SimpleSQLiteQuery(query));
        Pager<Integer,ExplorePlainView> pager=new Pager( new PagingConfig(9,3,false,9,600),()-> animePagingSourcePlain);
        return PagingRx.getFlowable(pager);
    }

    public Flowable<PagingData<ExplorePlainView>> getPersonalizedAnimePlain(String sortBy,String format){
        String query;
        if(sortBy.equals("Release Date")){
            query="SELECT animeID,title,imageURL,mediaType,rating,genres FROM ANIME WHERE format='"+format+"' ORDER BY date(releaseDate) DESC";
        }
        else{
            query="SELECT animeID,title,imageURL,mediaType,rating,genres FROM ANIME WHERE format='"+format+"' ORDER BY "+sortBy+" DESC";
        }
        animePagingSourcePlain.setQuery(new SimpleSQLiteQuery(query));
        Pager<Integer,ExplorePlainView> pager=new Pager( new PagingConfig(9,3,false,9,600),()-> animePagingSource);
        return PagingRx.getFlowable(pager);
    }

    public Flowable<PagingData<Animes>> searchAnimePage(String searchQuery){
        searchPagingSource.setSearchQuery(searchQuery);
        Pager<Integer,Animes> pager=new Pager( new PagingConfig(5,5,false,5,600),()-> searchPagingSource);
        return PagingRx.getFlowable(pager);
    }

    public Single<List<ExploreView>> getTrendingAnime(){
        return animeDao.getTrendingAnime();
    }

    public Single<List<ExploreView>> getPopularAnime(){
        return animeDao.getPopularAnime();
    }

    public Single<List<ExploreView>> getTop100Anime(){
        return animeDao.getTop100Anime();
    }

    public Single<List<ExploreView>> getTopUpcomingAnime(){
        return animeDao.getTopUpcomingAnime();
    }









    
    







}
