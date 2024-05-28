package com.omnicoder.anichan.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.database.UserAnime;
import com.omnicoder.anichan.models.animeListResponse.UserAnimeListResponse;
import com.omnicoder.anichan.repositories.AnimeListRepository;
import com.omnicoder.anichan.utils.Constants;

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
    public MutableLiveData<List<UserAnime>> searchResults = new MutableLiveData<>();
    public String nextPage = null;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<Boolean> animeListFetched = new MutableLiveData<>();
    public final MutableLiveData<Integer> sortBy=new MutableLiveData<>();
    private final AnimeListRepository repository;


    @Inject
    public AnimeListViewModel(AnimeListRepository repository) {
        this.repository = repository;
    }

    public String getNextPage() {
        return nextPage;
    }

    public MutableLiveData<Integer> getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortByInt){
        sortBy.setValue(sortByInt);
    }


    public MutableLiveData<List<UserAnime>> getSearchResults() {
        return searchResults;
    }


    public MutableLiveData<List<UserAnime>> getWatching() {
        return watching;
    }

    public MutableLiveData<List<UserAnime>> getPlanToWatch() {
        return planToWatch;
    }

    public MutableLiveData<List<UserAnime>> getCompleted() {
        return completed;
    }

    public MutableLiveData<List<UserAnime>> getOnHold() {
        return onHold;
    }

    public MutableLiveData<List<UserAnime>> getDropped() {
        return dropped;
    }

    public MutableLiveData<List<UserAnime>> getReWatching() {
        return reWatching;

    }


    public void fetchReWatching(int sortBy) {
        compositeDisposable.add(repository.getReWatching(sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(watching::setValue, Throwable::printStackTrace)
        );
    }


    public void fetchWatching(int sortBy) {
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.WATCHING, sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(watching::setValue, Throwable::printStackTrace)
        );
    }

    public void fetchAnimeByStatus(String status, MutableLiveData<List<UserAnime>> listMutableLiveData, int sortBy){
        compositeDisposable.add(repository.getAnimeListByStatus(status, sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listMutableLiveData::setValue, Throwable::printStackTrace)
        );
    }

    public void fetchPlanToWatch(int sortBy) {
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.PLAN_TO_WATCH, sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(planToWatch::setValue, Throwable::printStackTrace)
        );
    }

    public void fetchCompleted(int sortBy) {
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.COMPLETED, sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completed::setValue, Throwable::printStackTrace)
        );
    }

    public void fetchOnHold(int sortBy) {
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.ON_HOLD, sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onHold::setValue, Throwable::printStackTrace)
        );
    }

    public void fetchDropped(int sortBy) {
        compositeDisposable.add(repository.getAnimeListByStatus(Constants.DROPPED, sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dropped::setValue, Throwable::printStackTrace)
        );
    }


    public void searchAnime(CharSequence query) {
        compositeDisposable.add(repository.searchAnime(query.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResults::setValue, Throwable::printStackTrace)
        );
    }


    public void fetchUserAnimeList() {
        compositeDisposable.add(repository.fetchUserAnimeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(!response.getData().isEmpty()){
                        deleteAllAnime();
                        insertDataInDatabase(response);
                        nextPage = response.getPaging().getNext();
                    }
                }, Throwable::printStackTrace)
        );
    }

    public void deleteAllAnime() {
        compositeDisposable.add(repository.deleteAllAnime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());

    }

    public void insertDataInDatabase(UserAnimeListResponse userAnimeListResponse) {
        compositeDisposable.add(Single.fromCallable(() -> repository.insertAnimeInDB(userAnimeListResponse))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        animeListFetched.setValue(true);
                    } else {
                        animeListFetched.setValue(false);
                    }
                }));
    }


    public void fetchNextPage() {
        if (nextPage == null) return;
        compositeDisposable.add(repository.fetchNextPage(nextPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    nextPage = response.getPaging().getNext();
                    if (!response.getData().isEmpty()) {
                        insertDataInDatabase(response);
                    }
                }, Throwable::printStackTrace)
        );
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