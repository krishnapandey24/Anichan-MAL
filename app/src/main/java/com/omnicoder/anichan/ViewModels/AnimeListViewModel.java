package com.omnicoder.anichan.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.Models.AnimeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.Models.UpdateAnimeResponse;
import com.omnicoder.anichan.Repositories.AnimeListRepository;
import com.omnicoder.anichan.Repository;
import com.omnicoder.anichan.Utils.Constants;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
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
    private final AnimeListRepository repository;
    Repository repository1;

    @Inject
    public AnimeListViewModel(AnimeListRepository repository, Repository repository1){
        this.repository = repository;
        this.repository1=repository1;

    }


    public MutableLiveData<Boolean> getResponse() {
        return response;
    }

    public void addAnime(UserAnime userAnime){
        compositeDisposable.add(repository.addAnimeToList(userAnime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d("tagg","Inserted Successfully"), Throwable::printStackTrace)
        );
    }

    public MutableLiveData<List<UserAnime>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<List<UserAnime>> getAnimeList(int position){
        Log.d("tagg","Position"+position);
        switch (position){
            case 0:
                fetchWatching();
                return animeList;
            case 1:
                fetchPlanToWatch();
                return animeList1;
            case 2:
                fetchCompleted();
                return animeList2;
            case 3:
                fetchOnHold();
                return animeList3;
            case 4:
                fetchDropped();
                return animeList4;
            case 5:
                fetchReWatching();
                return animeList5;
            case 6:
                fetchAll();
                return animeList6;
        }
        return animeList;
    }

    private void fetchReWatching() {
        compositeDisposable.add(repository.getReWatching()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList::setValue,Throwable::printStackTrace)
        );
    }


    public void addEpisode(int id){
//        compositeDisposable.add(repository.addEpisode(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe()
//        );
    }


    public void fetchWatching(){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.WATCHING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchPlanToWatch(){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.PLAN_TO_WATCH)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList1::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchCompleted(){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.COMPLETED)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList2::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchOnHold(){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.ON_HOLD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList3::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchDropped(){
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.DROPPED)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList4::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchAll(){
        compositeDisposable.add(repository.getAllAnime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList6::setValue,Throwable::printStackTrace)
        );
    }

    public void animeCompleted(int id){
//        compositeDisposable.add(repository.animeCompleted(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe()
//        );
    }

    public void deleteAnime(int id){
//        compositeDisposable.add(repository.deleteAnime(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe()
//        );
    }

    public void searchAnime(CharSequence query){
//        compositeDisposable.add(repository.searchAnime(query)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(searchResults::setValue,Throwable::printStackTrace)
//        );
    }


    public void fetchUserAnimeList(){
        compositeDisposable.add(repository.fetchUserAnimeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    insertDataInDatabase(response);
                    nextPage.setValue(response.getPaging().getNext());
                })

        );
    }

    public void insertDataInDatabase(UserAnimeListResponse userAnimeListResponse){
        Single.fromCallable(() -> repository.insertAnimeInDB(userAnimeListResponse))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(b->{
            Log.d("tagg","returned le"+b);
        });
    }




    public void updateAnime(Integer id, String status, boolean isRewatching, Integer score, Integer numOfWatchedEpisodes){
        compositeDisposable.add(repository1.updateAnime(id,status,isRewatching,score,numOfWatchedEpisodes)
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

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}