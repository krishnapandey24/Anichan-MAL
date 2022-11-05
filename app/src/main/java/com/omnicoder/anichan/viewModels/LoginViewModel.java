package com.omnicoder.anichan.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.omnicoder.anichan.repositories.LoginRepository;
import com.omnicoder.anichan.utils.Constants;
import com.omnicoder.anichan.utils.PkceGenerator;
import com.omnicoder.anichan.utils.SessionManager;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


@HiltViewModel
public class LoginViewModel extends ViewModel {
    public static final String STATE="Anichan123";
    public MutableLiveData<Boolean> useExternalBrowser= new MutableLiveData<>(false);
    private final LoginRepository repository;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    SessionManager sessionManager;
    public MutableLiveData<Boolean> refreshComplete=new MutableLiveData<>();
    public MutableLiveData<Boolean> getNewAccessToken=new MutableLiveData<>();

    public MutableLiveData<Boolean> getRefreshComplete() {
        return refreshComplete;
    }

    @Inject
    public LoginViewModel(LoginRepository loginRepository,SessionManager sessionManager){
        this.repository= loginRepository;
        this.sessionManager=sessionManager;
    }




    public void setUseExternalBrowser(boolean useExternalBrowser){
        this.useExternalBrowser.setValue(useExternalBrowser);
    }

    public final String codeVerified= PkceGenerator.generateVerifier();
    public final String loginUrl= String.format(Constants.LOGIN_URL,Constants.CLIENT_ID,codeVerified,STATE);

    public void getAccessToken(String code){
        Log.d("tagg","function called access "+code);
        compositeDisposable.add(repository.getAccessToken(code,codeVerified)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    sessionManager.createSession(result);
                    getNewAccessToken.setValue(true);
                }, e->{
                    getNewAccessToken.setValue(false);
                    e.printStackTrace();
                    Log.d("tagg","the error : "+e.getMessage());
                })
        );
    }

    public void refreshAccessToken(String refreshToken){
        compositeDisposable.add(repository.refreshAccessToken(refreshToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newAccessToken->{
                    sessionManager.createSession(newAccessToken);
                    refreshComplete.setValue(true);
                    Log.d("tagg","latest refresh token: "+sessionManager.getLatestRefreshToken());
                },e->{
                    e.printStackTrace();
                    Log.d("tagg","Error in refresh access token viewmodel: "+e.getMessage());
                })
        );
    }

}