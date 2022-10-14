package com.omnicoder.anichan.ui.activities;

import static com.omnicoder.anichan.utils.Constants.DARK_MODE_TAG;

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
//        setTheme(R.style.Theme_Anichan);
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




    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }





}