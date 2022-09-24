package com.omnicoder.anichan.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Database.UserManga;
import com.omnicoder.anichan.Repositories.MangaListRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class UpdateMangaViewModel extends ViewModel {
    MutableLiveData<Boolean> response = new MutableLiveData<>();
    MutableLiveData<Boolean> updateMangaResponse = new MutableLiveData<>();
    MutableLiveData<Boolean> deleteResponse = new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    MangaListRepository repository;

    @Inject
    public UpdateMangaViewModel(MangaListRepository repository){
        this.repository=repository;
    }

    public MutableLiveData<Boolean> getResponse() {
        return response;
    }

    public MutableLiveData<Boolean> deleteResponse(){
        return deleteResponse;
    }

    public MutableLiveData<Boolean> getUpdateMangaResponse() {
        return updateMangaResponse;
    }
    

    public void updateManga(Integer id, String status, boolean isRewatching, Integer score, Integer noOfVolumesRead,Integer noOfChaptersRead){
        compositeDisposable.add(repository.updateManga(id,status,isRewatching,score,noOfVolumesRead,noOfChaptersRead)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(response!= null){
                        updateMangaResponse.setValue(true);
                    }else{
                        updateMangaResponse.setValue(false);
                    }
                }, throwable -> {
                    updateMangaResponse.setValue(false);
                    throwable.printStackTrace();
                })
        );
    }

    public void insertOrUpdateMangaInList(UserManga userManga){
        compositeDisposable.add(repository.addOrUpdateMangaInList(userManga)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }


    public void addChapter(int id,int noOfChaptersRead){
        compositeDisposable.add(repository.addChapter(id,noOfChaptersRead)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mangaResponse->{
                    response.setValue(true);
                }, e-> {
                    e.printStackTrace();
                    response.setValue(false);
                })
        );

        compositeDisposable.add(repository.addChapterInDB(id,noOfChaptersRead)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }
    public void mangaCompleted(int id){
        compositeDisposable.add(repository.mangaCompleted(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );

        compositeDisposable.add(repository.mangaCompletedInDB(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

    public void deleteManga(int id) {
        repository.deleteManga(id)
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

        compositeDisposable.add(repository.deleteMangaFromDB(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        );
    }

}
