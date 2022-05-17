package com.omnicoder.anichan.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Models.Responses.RankingResponse;
import com.omnicoder.anichan.Repositories.ExploreRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class ExploreViewModel extends ViewModel {

    private final MutableLiveData<List<Data>> trendingAnime= new MutableLiveData<>();
    private final MutableLiveData<List<Data>> top100Anime= new MutableLiveData<>();
    private final MutableLiveData<List<Data>> topUpcomingAnime = new MutableLiveData<>();
    private final MutableLiveData<List<Data>> allTimePopularAnime= new MutableLiveData<>();
    private final MutableLiveData<Boolean> startLoading= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final ExploreRepository exploreRepository;
    MutableLiveData<Boolean> NoInternet=new MutableLiveData<>();



    @Inject
    public ExploreViewModel(ExploreRepository exploreRepository){
        this.exploreRepository = exploreRepository;
    }


    public MutableLiveData<List<Data>> getAllTimePopularAnime() {
        return allTimePopularAnime;
    }

    public MutableLiveData<Boolean> getStartLoading() {
        return startLoading;
    }

    public MutableLiveData<List<Data>> getTopUpcomingAnime(){
        return topUpcomingAnime;
    }

    public MutableLiveData<List<Data>> getTrendingAnime(){
        return trendingAnime;
    }


    public MutableLiveData<List<Data>> getTop100Anime() {
        return top100Anime;
    }

    public MutableLiveData<Boolean> getNoInternet() {
        return NoInternet;
    }


    public void fetchTrending(){
        compositeDisposable.add(exploreRepository.get9TrendingAnime()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendingAnime::setValue, e->{
                    e.printStackTrace();
                    NoInternet.setValue(true);
                })
        );
    }

    public void fetchPopular(){
        compositeDisposable.add(exploreRepository.get9PopularAnime()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allTimePopularAnime::setValue, e->{
                    Log.d("tagg","Error: "+e.getMessage());
                    NoInternet.setValue(true);
                })
        );
    }

    public void fetchTop100(){
        compositeDisposable.add(exploreRepository.get9Top100Anime()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(top100Anime::setValue, e->{
                    Log.d("tagg","Error: "+e.getMessage());
                    NoInternet.setValue(true);
                })
        );
    }

    public void fetchTopUpcoming(){
        compositeDisposable.add(exploreRepository.get9TopUpcomingAnime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(RankingResponse::getData)
                .subscribe(topUpcomingAnime::setValue, e->{
                    Log.d("tagg","Error: "+e.getMessage());
                    NoInternet.setValue(true);
                })
        );
    }




    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }




}

