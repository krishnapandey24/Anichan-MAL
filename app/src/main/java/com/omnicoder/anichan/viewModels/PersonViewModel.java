package com.omnicoder.anichan.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.models.animeResponse.Characters.Person;
import com.omnicoder.anichan.models.jikan.ImageData;
import com.omnicoder.anichan.network.JikanApi;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class PersonViewModel extends ViewModel {
    MutableLiveData<Person> personDetails=new MutableLiveData<>();
    MutableLiveData<List<ImageData>> personImages=new MutableLiveData<>();
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    JikanApi jikanApi;

    @Inject
    public PersonViewModel(JikanApi jikanApi){
        this.jikanApi=jikanApi;
    }

    public MutableLiveData<Person> getPersonDetails() {
        return personDetails;
    }

    public MutableLiveData<List<ImageData>> getPersonImages() {
        return personImages;
    }

    public void fetchPersonDetails(int id){
        compositeDisposable.add(jikanApi.getPersonDetails(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> personDetails.setValue(response.getData()), e->{
                    personDetails.setValue(null);
                    e.printStackTrace();
                })
        );

        compositeDisposable.add(jikanApi.getPersonImages(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> personImages.setValue(response.getData()), e->{
                    personDetails.setValue(null);
                    e.printStackTrace();
                })
        );


    }

}
