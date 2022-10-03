package com.omnicoder.anichan.ui.activities;

import static com.omnicoder.anichan.utils.Constants.DARK_MODE_TAG;
import static com.omnicoder.anichan.utils.Constants.IS_PRO_USER;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.omnicoder.anichan.BuildConfig;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.ActivityMainBinding;
import com.omnicoder.anichan.utils.SessionManager;
import com.omnicoder.anichan.viewModels.MainViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NavController navController;
    @Inject
    SessionManager sessionManager;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Anichan);
        if(sharedPreferences.getBoolean(DARK_MODE_TAG,false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        if(sessionManager.notLoggedInOrTokenExpired()){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
        }
        MainViewModel mainViewModel=new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.fetchUserInfo();
        navController= Navigation.findNavController(this,R.id.fragmentContainerView);
        BottomNavigationView bottomNavigationView= binding.activityMainBottomNavigationView;
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
    }

    private void initializeGoogleAdmob(){
        if(!BuildConfig.DEBUG){
            if (!sharedPreferences.getBoolean(IS_PRO_USER, false)) {
                MobileAds.initialize(this);

                googleAdView = findViewById(R.id.adView)
                val adRequest = AdRequest.Builder().build()
                googleAdView.loadAd(adRequest)
                googleAdView.adListener = object : AdListener(){

                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        super.onAdFailedToLoad(p0)
                        Timber.e("Failed To Load Ad")
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        fragmentContainerView.setPadding(0,0,0,140)
                        googleAdView.show()
                        Timber.e("Ad Loaded Successfully")
                    }
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }



    // TODO: 29-Sep-22 Change viewModel initialization of all fragments


}