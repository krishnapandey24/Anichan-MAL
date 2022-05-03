package com.omnicoder.anichan.UI.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Anichan);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        NavController navController= Navigation.findNavController(this,R.id.fragmentContainerView);
        BottomNavigationView bottomNavigationView= binding.activityMainBottomNavigationView;
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));
        binding.imageView.setVisibility(View.GONE);
    }

    public void showNoInternetConnectionDialog(){
        Dialog noInternetConnectionDialog= new Dialog(MainActivity.this);
        noInternetConnectionDialog.setContentView(R.layout.no_internet_connection_dialog);
        noInternetConnectionDialog.setCancelable(false);
        Button okButton=noInternetConnectionDialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> noInternetConnectionDialog.dismiss());
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}