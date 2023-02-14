package com.omnicoder.anichan.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.database.UserAnime;
import com.omnicoder.anichan.repositories.AnimeListRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class UpdateAnimeViewModel extends ViewModel {
    MutableLiveData<Boolean> response = new MutableLiveData<>();
    MutableLiveData<Boolean> updateAnimeResponse = new MutableLiveData<>();
    MutableLiveData<Boolean> deleteResponse = new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    AnimeListRepository repository;

    @Inject
    public UpdateAnimeViewModel(AnimeListRepository repository){
        this.repository=repository;
    }

    public MutableLiveData<Boolean> getResponse() {
        return response;
    }

    public MutableLiveData<Boolean> getUpdateAnimeResponse() {
        return updateAnimeResponse;
    }

    public MutableLiveData<Boolean> deleteResponse(){
        return deleteResponse;
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
                    updateAnimeResponse.setValue(false);
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
                .subscribe(animeResponse -> response.setValue(true), e-> response.setValue(false))
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
        CompletableObserver deleteAnime=repository.deleteAnime(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        deleteResponse.setValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        deleteResponse.setValue(false);
                        e.printStackTrace();
                    }
                });

        compositeDisposable.add(repository.deleteAnimeFromDB(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }




}
