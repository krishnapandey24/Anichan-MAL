package com.omnicoder.anichan.ui.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.adapters.viewpagers.ThemesViewPagerAdapter;
import com.omnicoder.anichan.di.BaseApplication;
import com.omnicoder.anichan.databinding.ActivityViewThemesBinding;

public class ViewThemesActivity extends AppCompatActivity {
    ActivityViewThemesBinding binding;
    static final String[] themes={"Opening","Ending"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewThemesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewPager2 viewPager2= binding.viewPager;
        BaseApplication application= (BaseApplication) getApplicationContext();
        viewPager2.setAdapter(new ThemesViewPagerAdapter(this,application.getAnimeThemes()));
        TabLayout tabLayout=binding.tabLayout;
        new TabLayoutMediator(tabLayout,viewPager2,(tab, position) -> tab.setText(themes[position])).attach();
        binding.toolbar.setNavigationOnClickListener(v-> finish());
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

    }


}