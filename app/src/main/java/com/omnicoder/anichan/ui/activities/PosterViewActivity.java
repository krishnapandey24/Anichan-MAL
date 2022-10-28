package com.omnicoder.anichan.ui.activities;

import static com.omnicoder.anichan.utils.Constants.CHARACTER_IMAGES;
import static com.omnicoder.anichan.utils.Constants.IMAGE_TYPE;
import static com.omnicoder.anichan.utils.Constants.POSTERS;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.adapters.viewpagers.CharacterImageViewPagerAdapter;
import com.omnicoder.anichan.adapters.viewpagers.PostersViewPagerAdapter;
import com.omnicoder.anichan.databinding.ActivityPosterViewBinding;
import com.omnicoder.anichan.di.BaseApplication;
import com.omnicoder.anichan.models.jikan.ImageData;
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
        String imageType=getIntent().getStringExtra(IMAGE_TYPE);
        if(imageType.equals(POSTERS)){
            List<MainPicture> mainPictures=application.getPictures();
            ViewPager2 viewPager=binding.viewpager;
            viewPager.setAdapter(new PostersViewPagerAdapter(mainPictures));
            TabLayout tabLayout=binding.tabLayout;
            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();
        }else if(imageType.equals(CHARACTER_IMAGES)){
            List<ImageData> mainPictures=application.getCharacterImages();
            ViewPager2 viewPager=binding.viewpager;
            viewPager.setAdapter(new CharacterImageViewPagerAdapter(mainPictures));
            TabLayout tabLayout=binding.tabLayout;
            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();
        }else{
           //
        }


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