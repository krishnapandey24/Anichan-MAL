package com.omnicoder.anichan.viewModels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.database.UserManga;
import com.omnicoder.anichan.models.mangaListResponse.UserMangaListResponse;
import com.omnicoder.anichan.repositories.MangaListRepository;
import com.omnicoder.anichan.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class MangaListViewModel extends ViewModel {
    public MutableLiveData<List<UserManga>> reading= new MutableLiveData<>();
    public MutableLiveData<List<UserManga>> planToRead = new MutableLiveData<>();
    public MutableLiveData<List<UserManga>> completed = new MutableLiveData<>();
    public MutableLiveData<List<UserManga>> onHold = new MutableLiveData<>();
    public MutableLiveData<List<UserManga>> dropped = new MutableLiveData<>();
    public MutableLiveData<List<UserManga>> reReading = new MutableLiveData<>();
    public MutableLiveData<List<UserManga>> all = new MutableLiveData<>();
    public MutableLiveData<List<UserManga>> searchResults= new MutableLiveData<>();
    public MutableLiveData<String> nextPage= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final MutableLiveData<Boolean> mangaListFetched=new MutableLiveData<>();
    private final MangaListRepository repository;

    @Inject
    public MangaListViewModel(MangaListRepository mangaListRepository){
        this.repository = mangaListRepository;

    }


    public MutableLiveData<List<UserManga>> getSearchResults() {
        return searchResults;
    }

    public MutableLiveData<List<UserManga>> getMangaList(int position, String sortBy){
        switch (position){
            case 0:
                fetchReading(sortBy);
                return reading;
            case 1:
                fetchPlanToWatch(sortBy);
                return planToRead;
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
                fetchReReading(sortBy);
                return reReading;
            case 6:
                fetchAll(sortBy);
                return all;
        }
        return reading;
    }

    private void fetchReReading(String sortBy) {
        compositeDisposable.add(repository.getReReading(sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reading::setValue,Throwable::printStackTrace)
        );
    }




    public void fetchReading(String sortBy){
        compositeDisposable.add(repository.getMangaListByStatus(Constants.READING,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reading::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchPlanToWatch(String sortBy){
        compositeDisposable.add(repository.getMangaListByStatus(Constants.PLAN_TO_READ,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(planToRead::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchCompleted(String sortBy){
        compositeDisposable.add(repository.getMangaListByStatus(Constants.COMPLETED,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completed::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchOnHold(String sortBy){
        compositeDisposable.add(repository.getMangaListByStatus(Constants.ON_HOLD,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onHold::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchDropped(String sortBy){
        compositeDisposable.add(repository.getMangaListByStatus(Constants.DROPPED,sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dropped::setValue,Throwable::printStackTrace)
        );
    }

    public void fetchAll(String sortBy){
        compositeDisposable.add(repository.getAllManga(sortBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(all::setValue,Throwable::printStackTrace)
        );
    }

    public void searchManga(CharSequence query){
        compositeDisposable.add(repository.searchManga(query.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResults::setValue,Throwable::printStackTrace)
        );
    }


    public void fetchUserMangaList(){
        compositeDisposable.add(repository.fetchUserMangaList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    deleteAllManga();
                    insertDataInDatabase(response);
                    nextPage.setValue(response.getPaging().getNext());
                })

        );
    }

    public void deleteAllManga(){
        compositeDisposable.add(repository.deleteAllManga()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());

    }

    public void insertDataInDatabase(UserMangaListResponse userMangaListResponse){
        Single.fromCallable(() -> repository.insertMangaInDB(userMangaListResponse))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if(aBoolean){
                        mangaListFetched.setValue(true);
                    }else{
                        mangaListFetched.setValue(false);
                    }
                });


    }

    public MutableLiveData<Boolean> getMangaListFetchedStatus() {
        return mangaListFetched;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}