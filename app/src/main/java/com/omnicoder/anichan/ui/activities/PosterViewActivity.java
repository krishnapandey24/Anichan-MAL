package com.omnicoder.anichan.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.adapters.viewpagers.PostersViewPagerAdapter;
import com.omnicoder.anichan.databinding.ActivityPosterViewBinding;
import com.omnicoder.anichan.di.BaseApplication;
import com.omnicoder.anichan.models.responses.MainPicture;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class PosterViewActivity extends AppCompatActivity {
    ActivityPosterViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPosterViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BaseApplication application= (BaseApplication) getApplicationContext();
        List<MainPicture> mainPictures=application.getPictures();
        ViewPager2 viewPager=binding.viewpager;
        viewPager.setAdapter(new PostersViewPagerAdapter(mainPictures));
        TabLayout tabLayout=binding.tabLayout;
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}