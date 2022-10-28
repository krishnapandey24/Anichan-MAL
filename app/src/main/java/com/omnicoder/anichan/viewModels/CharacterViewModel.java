package com.omnicoder.anichan.viewModels;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.models.animeResponse.Characters.CharacterData;
import com.omnicoder.anichan.models.animeResponse.Characters.Jpg;
import com.omnicoder.anichan.models.jikan.CharacterDetailsData;
import com.omnicoder.anichan.models.jikan.CharacterDetailsResponse;
import com.omnicoder.anichan.models.jikan.ImageData;
import com.omnicoder.anichan.network.JikanApi;
import com.omnicoder.anichan.network.MalApi;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class CharacterViewModel extends ViewModel {
    MutableLiveData<CharacterDetailsData> characterDetails=new MutableLiveData<>();
    MutableLiveData<List<ImageData>> characterImages=new MutableLiveData<>();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    JikanApi jikanApi;

    @Inject
    public CharacterViewModel(JikanApi jikanApi){
        this.jikanApi=jikanApi;
    }

    public MutableLiveData<CharacterDetailsData> getCharacterDetails() {
        return characterDetails;
    }

    public MutableLiveData<List<ImageData>> getCharacterImages() {
        return characterImages;
    }

    public void fetchCharacterDetails(int id){
        compositeDisposable.add(jikanApi.getCharactersDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> characterDetails.setValue(response.getData()), Throwable::printStackTrace)
        );

        compositeDisposable.add(jikanApi.getCharacterImages(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> characterImages.setValue(response.getData()), Throwable::printStackTrace)
        );


    }
}
