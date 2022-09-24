package com.omnicoder.anichan.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.Models.AnimeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.Repositories.AnimeListRepository;
import com.omnicoder.anichan.Utils.Constants;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class AnimeListViewModel extends ViewModel {
    public MutableLiveData<List<UserAnime>> watching = new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> planToWatch = new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> completed = new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> onHold = new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> dropped = new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> reWatching = new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> all = new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> searchResults= new MutableLiveData<>();
    public MutableLiveData<String> nextPage= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final MutableLiveData<Boolean> animeListFetched=new MutableLiveData<>();
    private final AnimeListRepository repository;


    @Inject
    public AnimeListViewModel(AnimeListRepository repository){
        this.repository = repository;

    }


    public MutableLiveData<List<UserAnime>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<List<UserAnime>> getAnimeList(int position, String sortBy){
        switch (position){
            case 0:
                fetchWatching(sortBy);
                return watching;
            case 1:
                fetchPlanToWatch(sortBy);
                return planToWatch;
            case 2:
                fetchCompleted(sortBy);
                return completed;
            case 3:
                fetchOnHold(sortBy);
                return onHold;
            case 4:
                fetchDropped(sortBy);
                return dropped;
            case 5:
                fetchReWatching(sortBy);
                return reWatching;
            case 6:
                fetchAll(sortBy);
                return all;
        }
        return watching;
    }

    private void fetchReWatching(String sortBy) {
        compositeDisposable.add(repository.getReWatching(sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(watching::setValue,Throwable::printStackTrace)
        );
    }




    public void fetchWatching(String sortBy){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.WATCHING,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(watching::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchPlanToWatch(String sortBy){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.PLAN_TO_WATCH,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(planToWatch::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchCompleted(String sortBy){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.COMPLETED,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completed::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchOnHold(String sortBy){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.ON_HOLD,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onHold::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchDropped(String sortBy){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.DROPPED,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dropped::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchAll(String sortBy){
        compositeDisposable.add(repository.getAllAnime(sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(all::setValue,Throwable::printStackTrace)
        );
    }

    public void searchAnime(CharSequence query){
        compositeDisposable.add(repository.searchAnime(query.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResults::setValue,Throwable::printStackTrace)
        );
    }


    public void updateAnime(Integer id, String status, boolean isRewatching, Integer score, Integer numOfWatchedEpisodes){
        compositeDisposable.add(repository.updateAnime(id,status,isRewatching,score,numOfWatchedEpisodes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updateAnimeResponse -> {
                    if(updateAnimeResponse!=null){
                        Log.d("tagg","status: "+updateAnimeResponse.getStatus());
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Log.d("tagg","Error: "+throwable.getMessage());
                })
        );

    }

    public void fetchUserAnimeList(){
        compositeDisposable.add(repository.fetchUserAnimeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    deleteAllAnime();
                    insertDataInDatabase(response);
                    nextPage.setValue(response.getPaging().getNext());
                })
        );
    }

    public void deleteAllAnime(){
        compositeDisposable.add(repository.deleteAllAnime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());

    }

    public void insertDataInDatabase(UserAnimeListResponse userAnimeListResponse){
        Single.fromCallable(() -> repository.insertAnimeInDB(userAnimeListResponse))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if(aBoolean){
                        animeListFetched.setValue(true);
                    }else{
                        animeListFetched.setValue(false);
                    }
                });


    }

    public MutableLiveData<Boolean> getAnimeListFetchedStatus() {
        return animeListFetched;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}