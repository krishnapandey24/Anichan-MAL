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
    private final MutableLiveData<List<Data>> topUpcomingAnime = new MutableLiveData<>();
    private final MutableLiveData<List<Data>> recommendation=new MutableLiveData<>();
    private final MutableLiveData<Boolean> startLoading= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final ExploreRepository exploreRepository;



    @Inject
    public ExploreViewModel(ExploreRepository exploreRepository){
        this.exploreRepository = exploreRepository;
    }


    public MutableLiveData<List<Data>> getTopUpcomingAnime(){
        return topUpcomingAnime;
    }

    public MutableLiveData<List<Data>> getTrendingAnime(){
        return trendingAnime;
    }

    public MutableLiveData<List<Data>> getRecommendation() {
        return recommendation;
    }

    public void fetchTrending(){
        compositeDisposable.add(exploreRepository.get9TrendingAnime()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendingAnime::setValue,e->{
                    e.printStackTrace();
                })
        );
    }

    public void fetchSuggestions(){
        compositeDisposable.add(exploreRepository.getSuggestions()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommendation::setValue,e->{
                    e.printStackTrace();
                })
        );
    }


    public void fetchTopUpcoming(){
        compositeDisposable.add(exploreRepository.get9TopUpcomingAnime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(RankingResponse::getData)
                .subscribe(topUpcomingAnime::setValue,e->{
                    e.printStackTrace();
                })
        );
    }




    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }




}

