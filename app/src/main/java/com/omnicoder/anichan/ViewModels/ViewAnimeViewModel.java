package com.omnicoder.anichan.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.Models.AnimeResponse.videos.Promo;
import com.omnicoder.anichan.Repositories.ExploreRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ViewAnimeViewModel extends ViewModel {
    MutableLiveData<Anime> anime= new MutableLiveData<>();
    MutableLiveData<List<Promo>> videos= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final ExploreRepository repository;
    MutableLiveData<Boolean> NoInternet=new MutableLiveData<>();

    @Inject
    public ViewAnimeViewModel(ExploreRepository repository){
        this.repository=repository;
    }

    public MutableLiveData<Anime> getAnimeDetails() {
        return anime;
    }

    public void fetchAnimeDetails(int id){
        compositeDisposable.add(repository.getAnime(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(anime::setValue, e->{
                    e.printStackTrace();
                    NoInternet.setValue(true);
                    Log.d("tagg","Error: in fetch "+e.getMessage());
                })
        );
    }

    public void fetchVideos(int id){
        compositeDisposable.add(repository.getVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videoResponse -> {
                    videos.setValue(videoResponse.getData().getPromo());
                })
        );
    }

    public MutableLiveData<List<Promo>> getVideos() {
        return videos;
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
