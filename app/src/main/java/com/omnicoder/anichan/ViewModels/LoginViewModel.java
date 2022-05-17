package com.omnicoder.anichan.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.Models.AccessToken;
import com.omnicoder.anichan.Repositories.ExploreRepository;
import com.omnicoder.anichan.Utils.Constants;
import com.omnicoder.anichan.Utils.PkceGenerator;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class LoginViewModel extends ViewModel {
    public static final String STATE="Anichan123";
    public MutableLiveData<Boolean> useExternalBrowser= new MutableLiveData<>(false);
    public MutableLiveData<AccessToken> accessToken=new MutableLiveData<>();
    private ExploreRepository repository;
    CompositeDisposable compositeDisposable= new CompositeDisposable();



    @Inject
    public LoginViewModel(ExploreRepository exploreRepository){
        this.repository= exploreRepository;
    }




    public void setUseExternalBrowser(boolean useExternalBrowser){
        this.useExternalBrowser.setValue(useExternalBrowser);
    }

    public final String codeVerified= PkceGenerator.generateVerifier();
    public final String loginUrl= String.format(Constants.LOGIN_URL,Constants.CLIENT_ID,codeVerified,STATE);

    public void getAccessToken(String code){
        Log.d("tagg","method called"+code);
        compositeDisposable.add(repository.getAccessToken(code,codeVerified)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    accessToken.setValue(result);
                }, e->{
                    e.printStackTrace();
                    Log.d("tagg","the rroer : "+e.getMessage());
                })
        );
    }






}
