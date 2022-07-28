package com.omnicoder.anichan.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Database.AnimeList;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeListStatus;
import com.omnicoder.anichan.Repositories.AnimeListRepository;
import com.omnicoder.anichan.Utils.Constants;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class AnimeListViewModel extends ViewModel {
    public MutableLiveData<List<AnimeList>> animeList= new MutableLiveData<>();
    public MutableLiveData<List<AnimeList>> animeList1= new MutableLiveData<>();
    public MutableLiveData<List<AnimeList>> animeList2= new MutableLiveData<>();
    public MutableLiveData<List<AnimeList>> animeList3= new MutableLiveData<>();
    public MutableLiveData<List<AnimeList>> animeList4= new MutableLiveData<>();
    public MutableLiveData<List<AnimeList>> animeList5= new MutableLiveData<>();
    public MutableLiveData<List<AnimeList>> animeList6= new MutableLiveData<>();
    public MutableLiveData<List<AnimeList>> searchResults= new MutableLiveData<>();
    public MutableLiveData<Boolean> response= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final AnimeListRepository repository;

    @Inject
    public AnimeListViewModel(AnimeListRepository repository){
        this.repository = repository;
    }


    public void addAnime(AnimeList animeListItem){
        compositeDisposable.add(repository.addAnimeToList(animeListItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(()-> Log.d("tagg","Inserted Successfully"), Throwable::printStackTrace)
        );
    }

    public MutableLiveData<List<AnimeList>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<List<AnimeList>> getAnimeList(int position){
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
                fetchAll();
                return animeList6;
        }
        return animeList;
    }


    public void addEpisode(int id){
        compositeDisposable.add(repository.addEpisode(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void fetchWatching(){
        compositeDisposable.add(repository.getAnimeList(Constants.WATCHING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchPlanToWatch(){
        compositeDisposable.add(repository.getAnimeList(Constants.PLAN_TO_WATCH)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList1::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchCompleted(){
        compositeDisposable.add(repository.getAnimeList(Constants.COMPLETED)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList2::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchOnHold(){
        compositeDisposable.add(repository.getAnimeList(Constants.ON_HOLD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList3::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchDropped(){
        compositeDisposable.add(repository.getAnimeList(Constants.DROPPED)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList4::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchAll(){
        compositeDisposable.add(repository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeList6::setValue,Throwable::printStackTrace)
        );
    }

    public void animeCompleted(int id){
        compositeDisposable.add(repository.animeCompleted(id)
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
    }

    public void searchAnime(CharSequence query){
        compositeDisposable.add(repository.searchAnime(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResults::setValue,Throwable::printStackTrace)
        );
    }


    public void updateAnime(int anime_id, String status, boolean is_rewatching, int num_of_episodes_watched, int score, String start_date, String finish_date){
        compositeDisposable.add(repository.updateAnimeList(anime_id,status,is_rewatching,num_of_episodes_watched,score,start_date,finish_date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> response.setValue(true), e-> {
                    e.printStackTrace();
                    response.setValue(false);
                })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}