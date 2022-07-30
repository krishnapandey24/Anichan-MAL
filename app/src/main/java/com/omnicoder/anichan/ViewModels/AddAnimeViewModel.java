package com.omnicoder.anichan.ViewModels;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AddAnimeViewModel extends ViewModel {
    MutableLiveData<Boolean> response = new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    Repository repository;

    @Inject
    public AddAnimeViewModel(Repository repository){
        this.repository=repository;
    }

    public MutableLiveData<Boolean> getResponse() {
        return response;
    }

    public void updateAnime(Integer id, String status, boolean isRewatching, Integer score, Integer numOfWatchedEpisodes){
        compositeDisposable.add(repository.updateAnime(id,status,isRewatching,score,numOfWatchedEpisodes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateAnimeResponse -> {
                    Log.d("tagg","is null: "+(updateAnimeResponse==null));
                    if(updateAnimeResponse!=null){
                        Log.d("tagg","status: "+updateAnimeResponse.getStatus());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Log.d("tagg","Error: "+throwable.getMessage());
                })
        );

    }
}
