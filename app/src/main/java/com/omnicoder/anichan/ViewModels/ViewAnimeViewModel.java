package com.omnicoder.anichan.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Models.ViewAnime;
import com.omnicoder.anichan.Repositories.ViewAnimeRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ViewAnimeViewModel extends ViewModel {
    MutableLiveData<ViewAnime> animeDetails= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final ViewAnimeRepository repository;
    MutableLiveData<Boolean> NoInternet=new MutableLiveData<>();

    @Inject
    public ViewAnimeViewModel(ViewAnimeRepository repository){
        this.repository=repository;
    }

    public MutableLiveData<ViewAnime> getAnimeDetails() {
        return animeDetails;
    }

    public void fetchAnimeDetails(String media_type,int id,String extra){
        compositeDisposable.add(repository.fetchAnimeDetails(media_type,id,extra)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(animeDetails::setValue, e->{
                    e.printStackTrace();
                    NoInternet.setValue(true);
                    Log.d("tagg","Error: in fetch "+e.getMessage());
                })
        );
    }

    public MutableLiveData<Boolean> getNoInternet() {
        return NoInternet;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }


}
