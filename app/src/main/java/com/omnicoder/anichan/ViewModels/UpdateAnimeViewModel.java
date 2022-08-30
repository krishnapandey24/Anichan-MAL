package com.omnicoder.anichan.ViewModels;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.Repository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class UpdateAnimeViewModel extends ViewModel {
    MutableLiveData<Boolean> response = new MutableLiveData<>();
    MutableLiveData<Boolean> updateAnimeResponse = new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    Repository repository;

    @Inject
    public UpdateAnimeViewModel(Repository repository){
        this.repository=repository;
    }

    public MutableLiveData<Boolean> getResponse() {
        return response;
    }

    public MutableLiveData<Boolean> getUpdateAnimeResponse() {
        return updateAnimeResponse;
    }



    public void updateAnime(Integer id, String status, boolean isRewatching, Integer score, Integer numOfWatchedEpisodes){
        compositeDisposable.add(repository.updateAnime(id,status,isRewatching,score,numOfWatchedEpisodes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(response!=null){
                        updateAnimeResponse.setValue(true);
                    }else{
                        updateAnimeResponse.setValue(false);
                    }
                }, throwable -> {
                    response.setValue(false);
                    throwable.printStackTrace();
                })
        );
    }

    public void insertOrUpdateAnimeInList(UserAnime userAnime){
        compositeDisposable.add(repository.addOrUpdateAnimeInList(userAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void addEpisode(int id,int numberOfEpisodesWatched){
        compositeDisposable.add(repository.addEpisode(id,numberOfEpisodesWatched)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> response.setValue(true), e-> response.setValue(false))
        );

        compositeDisposable.add(repository.addEpisodeInDB(id,numberOfEpisodesWatched)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }
    public void animeCompleted(int id){
        compositeDisposable.add(repository.animeCompleted(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );

        compositeDisposable.add(repository.animeCompletedInDB(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void deleteAnime(int id){
        compositeDisposable.add(repository.deleteAnime(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
        compositeDisposable.add(repository.deleteAnimeFromDB(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }


}
