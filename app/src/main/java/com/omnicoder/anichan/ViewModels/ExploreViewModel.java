package com.omnicoder.anichan.ViewModels;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Database.Anime;
import com.omnicoder.anichan.Models.ExploreView;
import com.omnicoder.anichan.Models.TrendingAnime;
import com.omnicoder.anichan.Models.ViewAnime;
import com.omnicoder.anichan.Repositories.ExploreRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class ExploreViewModel extends ViewModel {

    private final MutableLiveData<List<TrendingAnime>> trendingAnime= new MutableLiveData<>();
    private final MutableLiveData<List<ExploreView>> top100Anime= new MutableLiveData<>();
    private final MutableLiveData<List<ExploreView>> topUpcomingAnime = new MutableLiveData<>();
    private final MutableLiveData<List<ExploreView>> allTimePopularAnime= new MutableLiveData<>();
    private final MutableLiveData<Boolean> startLoading= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final ExploreRepository exploreRepository;
    MutableLiveData<Boolean> NoInternet=new MutableLiveData<>();



    @Inject
    public ExploreViewModel(ExploreRepository exploreRepository){
        this.exploreRepository = exploreRepository;
    }


    public MutableLiveData<List<ExploreView>> getAllTimePopularAnime() {
        return allTimePopularAnime;
    }

    public MutableLiveData<Boolean> getStartLoading() {
        return startLoading;
    }

    public MutableLiveData<List<ExploreView>> getTopUpcomingAnime(){
        return topUpcomingAnime;
    }

    public MutableLiveData<List<TrendingAnime>> getTrendingAnime(){
        return trendingAnime;
    }


    public MutableLiveData<List<ExploreView>> getTop100Anime() {
        return top100Anime;
    }

    public MutableLiveData<Boolean> getNoInternet() {
        return NoInternet;
    }


    public void fetchTrending(){
        compositeDisposable.add(exploreRepository.get9TrendingAnime()
                .subscribeOn(Schedulers.io())
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
                .subscribe(topUpcomingAnime::setValue, e->{
                    Log.d("tagg","Error: "+e.getMessage());
                    NoInternet.setValue(true);
                })
        );
    }

    public void fetchAll(){
        reset();
        compositeDisposable.add(exploreRepository.fetchAllAnime()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(allAnime -> {
                    insertAllAnime(allAnime.getAllAnime());
                },e->{
                    Log.d("tagg","Error: fetchall "+e.getMessage());
                    NoInternet.setValue(true);
                })
        );
    }


    public void insertAllAnime(List<Anime> animeList){
        compositeDisposable.add(exploreRepository.insertAllAnime(animeList)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()->startLoading.setValue(Boolean.FALSE), e->Log.d("tagg","Error: insettAll "+e.getMessage()))
        );

    }

    public void reset(){
        compositeDisposable.add(exploreRepository.reset()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d("tagg","Completed"), e->Log.d("tagg","Error: insettAll "+e.getMessage()))
        );
    }

    public void check(Context context){
        Single.fromCallable(() -> updateData(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(startLoading::setValue,e->Log.d("tagg","Error: "+e.getMessage()));
    }

    private boolean updateData(Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences("DATA",Context.MODE_PRIVATE);
        Date today= new Date(System.currentTimeMillis());
        long date= sharedPreferences.getLong("Date",86400001);
        Log.d("tagg","Hey"+date);
        Date storedDate= new Date(date);
        long timeDifference= today.getTime()-storedDate.getTime();
        if(timeDifference>=86400000){
            fetchAll();
            sharedPreferences.edit().putLong("Date",today.getTime()).apply();
        }
        if(date==86400001){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }




    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }




}

