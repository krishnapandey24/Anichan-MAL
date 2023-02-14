package com.omnicoder.anichan.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.models.animeResponse.Characters.CharacterData;
import com.omnicoder.anichan.models.animeResponse.Staff.StaffData;
import com.omnicoder.anichan.models.animeResponse.videos.Promo;
import com.omnicoder.anichan.repositories.ExploreRepository;

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
    MutableLiveData<List<CharacterData>> characters= new MutableLiveData<>();
    MutableLiveData<List<StaffData>> staff= new MutableLiveData<>();
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
                    NoInternet.setValue(true);
                })
        );
    }

    public void fetchVideos(int id){
        compositeDisposable.add(repository.getVideos(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videoResponse -> videos.setValue(videoResponse.getData().getPromo()),Throwable::printStackTrace)
        );
    }

    public void fetchCharacters(int id){
        compositeDisposable.add(repository.getCharacters(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(characterResponse -> characters.setValue(characterResponse.getData()), Throwable::printStackTrace)
        );
    }

    public void fetchStaff(int id){
        compositeDisposable.add(repository.getStaff(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(staffResponse -> staff.setValue(staffResponse.getStaffData()))
        );
    }

    public MutableLiveData<List<Promo>> getVideos() {
        return videos;
    }

    public MutableLiveData<List<CharacterData>> getCharacters() {
        return characters;
    }

    public MutableLiveData<List<StaffData>> getStaff() {
        return staff;
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
