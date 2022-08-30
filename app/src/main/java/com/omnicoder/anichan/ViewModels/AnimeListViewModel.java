package com.omnicoder.anichan.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.Models.AnimeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.Repositories.AnimeListRepository;
import com.omnicoder.anichan.Repository;
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
    public MutableLiveData<List<UserAnime>> animeList= new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> animeList1= new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> animeList2= new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> animeList3= new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> animeList4= new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> animeList5= new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> animeList6= new MutableLiveData<>();
    public MutableLiveData<List<UserAnime>> searchResults= new MutableLiveData<>();
    public MutableLiveData<Boolean> response= new MutableLiveData<>();
    public MutableLiveData<String> nextPage= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final AnimeListRepository animeListRepository;
    Repository repository;

    @Inject
    public AnimeListViewModel(AnimeListRepository animeListRepository, Repository repository){
        this.animeListRepository = animeListRepository;
        this.repository =repository;

    }


    public MutableLiveData<Boolean> getResponse() {
        return response;
    }

    public void addAnime(UserAnime userAnime){
        compositeDisposable.add(animeListRepository.addAnimeToList(userAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d("tagg","Inserted Successfully"), Throwable::printStackTrace)
        );
    }

    public MutableLiveData<List<UserAnime>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<List<UserAnime>> getAnimeList(int position, String sortBy){
        switch (position){
            case 0:
                fetchWatching(sortBy);
                return animeList;
            case 1:
                fetchPlanToWatch(sortBy);
                return animeList1;
            case 2:
                fetchCompleted(sortBy);
                return animeList2;
            case 3:
                fetchOnHold(sortBy);
                return animeList3;
            case 4:
                fetchDropped(sortBy);
                return animeList4;
            case 5:
                fetchReWatching(sortBy);
                return animeList5;
            case 6:
                fetchAll(sortBy);
                return animeList6;
        }
        return animeList;
    }

    private void fetchReWatching(String sortBy) {
        compositeDisposable.add(animeListRepository.getReWatching(sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList::setValue,Throwable::printStackTrace)
        );
    }




    public void fetchWatching(String sortBy){
        compositeDisposable.add(animeListRepository.getAnimeListByStatus(Constants.WATCHING,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchPlanToWatch(String sortBy){
        compositeDisposable.add(animeListRepository.getAnimeListByStatus(Constants.PLAN_TO_WATCH,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList1::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchCompleted(String sortBy){
        compositeDisposable.add(animeListRepository.getAnimeListByStatus(Constants.COMPLETED,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList2::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchOnHold(String sortBy){
        compositeDisposable.add(animeListRepository.getAnimeListByStatus(Constants.ON_HOLD,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList3::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchDropped(String sortBy){
        compositeDisposable.add(animeListRepository.getAnimeListByStatus(Constants.DROPPED,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList4::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchAll(String sortBy){
        compositeDisposable.add(animeListRepository.getAllAnime(sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList6::setValue,Throwable::printStackTrace)
        );
    }

    public void searchAnime(CharSequence query){
        compositeDisposable.add(animeListRepository.searchAnime(query.toString())
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







    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}