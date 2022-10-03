package com.omnicoder.anichan.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.ActivityViewCharacterBinding;

public class ViewCharacterActivity extends AppCompatActivity {
    ActivityViewCharacterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewCharacterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AdRequest adRequest= new AdRequest.Builder().build();
        com.google.android.gms.ads.AdView adView = binding.adView;
        adView.loadAd(adRequest);
        AdListener adListener=new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        };
        adView.setAdListener(adListener);
        // TODO: 29-Sep-22 Complete ViewCharacterActivity 
    }
}