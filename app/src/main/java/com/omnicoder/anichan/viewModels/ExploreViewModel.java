package com.omnicoder.anichan.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Models.Responses.RankingResponse;
import com.omnicoder.anichan.Repositories.ExploreRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class ExploreViewModel extends ViewModel {

    private final MutableLiveData<List<Data>> trendingAnime= new MutableLiveData<>();
    private final MutableLiveData<List<Data>> topUpcomingAnime = new MutableLiveData<>();
    private final MutableLiveData<List<Data>> recommendation=new MutableLiveData<>();
    private final MutableLiveData<List<Data>> topManga=new MutableLiveData<>();
    private final MutableLiveData<List<Data>> topManhwa=new MutableLiveData<>();
    private final MutableLiveData<List<Data>> topManhua=new MutableLiveData<>();

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

    public MutableLiveData<List<Data>> getTopManga() {
        return topManga;
    }

    public MutableLiveData<List<Data>> getTopManhwa() {
        return topManhwa;
    }

    public MutableLiveData<List<Data>> getTopManhua() {
        return topManhua;
    }


    public void fetchTrending(){
        compositeDisposable.add(exploreRepository.get9TrendingAnime()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendingAnime::setValue, Throwable::printStackTrace)
        );
    }

    public void fetchSuggestions(){
        compositeDisposable.add(exploreRepository.getSuggestions()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recommendation::setValue, Throwable::printStackTrace)
        );
    }


    public void fetchTopUpcoming(){
        compositeDisposable.add(exploreRepository.get9TopUpcomingAnime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(RankingResponse::getData)
                .subscribe(topUpcomingAnime::setValue, Throwable::printStackTrace)
        );
    }


    public void fetchTopManga(){
        compositeDisposable.add(exploreRepository.get9TopManga()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topManga::setValue, Throwable::printStackTrace)
        );
    }

    public void fetchTopManhwa(){
        compositeDisposable.add(exploreRepository.get9TopManhwa()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topManhwa::setValue, Throwable::printStackTrace)
        );
    }

    public void fetchTopManhua(){
        compositeDisposable.add(exploreRepository.get9TopManhua()
                .subscribeOn(Schedulers.io())
                .map(RankingResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topManhua::setValue, Throwable::printStackTrace)
        );
    }





    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }




}

