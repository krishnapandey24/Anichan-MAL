package com.omnicoder.anichan.viewModels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.models.animeResponse.Characters.CharacterData;
import com.omnicoder.anichan.models.mangaResponse.Manga;
import com.omnicoder.anichan.repositories.ExploreRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MangaDetailsViewModel extends ViewModel {
    MutableLiveData<Manga> manga= new MutableLiveData<>();
    MutableLiveData<List<CharacterData>> characters= new MutableLiveData<>();
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    private final ExploreRepository repository;
    MutableLiveData<Boolean> NoInternet=new MutableLiveData<>();

    @Inject
    public MangaDetailsViewModel(ExploreRepository repository){
        this.repository=repository;
    }

    public MutableLiveData<Manga> getMangaDetails() {
        return manga;
    }

    public void fetchMangaDetails(int id){
        compositeDisposable.add(repository.getManga(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(manga::setValue, e-> NoInternet.setValue(true))
        );
    }

    public void fetchCharacters(int id){
        compositeDisposable.add(repository.getMangaCharacters(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(characterResponse -> characters.setValue(characterResponse.getData()), Throwable::printStackTrace)
        );
    }





    public MutableLiveData<List<CharacterData>> getCharacters() {
        return characters;
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
