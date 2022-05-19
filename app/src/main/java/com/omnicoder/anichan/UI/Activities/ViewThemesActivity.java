package com.omnicoder.anichan.UI.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.Adapters.ThemesViewPagerAdapter;
import com.omnicoder.anichan.DI.BaseApplication;
import com.omnicoder.anichan.databinding.ActivityViewThemesBinding;

public class ViewThemesActivity extends AppCompatActivity {
    ActivityViewThemesBinding binding;
    static final String[] themes={"Opening","Ending"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewThemesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backButton3.setOnClickListener(v -> finish());
        ViewPager2 viewPager2= binding.viewPager;
        BaseApplication application= (BaseApplication) getApplicationContext();
        viewPager2.setAdapter(new ThemesViewPagerAdapter(this,application.getAnimeThemes()));
        TabLayout tabLayout=binding.tabLayout2;
        new TabLayoutMediator(tabLayout,viewPager2,(tab, position) -> tab.setText(themes[position])).attach();
    }

}