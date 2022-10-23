package com.omnicoder.anichan.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.stateAdapters.ViewMangaStateAdapter;
import com.omnicoder.anichan.databinding.ActivityViewMangaBinding;
import com.omnicoder.anichan.di.BaseApplication;
import com.omnicoder.anichan.models.mangaResponse.Manga;
import com.omnicoder.anichan.models.responses.Genre;
import com.omnicoder.anichan.models.responses.MainPicture;
import com.omnicoder.anichan.ui.fragments.bottomSheets.AddMangaBottomSheet;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.MangaDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ViewMangaActivity extends AppCompatActivity implements AddMangaBottomSheet.MangaAdded {
    ActivityViewMangaBinding binding;
    MangaDetailsViewModel viewModel;
    boolean viewMore=true;
    AddMangaBottomSheet addMangaBottomSheet;
    boolean addedToList=false;
    LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 09-Oct-22 Add Pictures view
        loadingDialog=new LoadingDialog(this);
        loadingDialog.startLoadingForActivity();
        binding = ActivityViewMangaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(MangaDetailsViewModel.class);
        Intent intent = getIntent();
        viewModel.fetchMangaDetails(intent.getIntExtra("id", 0));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        addMangaBottomSheet=new AddMangaBottomSheet();
        observeData();
        setOnClickListeners();
    }

    private void setTabLayout(Manga manga) {
        String[] tabs=getResources().getStringArray(R.array.ViewTabs);
        ViewPager2 viewPager;
        viewPager=binding.fragmentContainerView;
        FragmentStateAdapter fragmentStateAdapter;
        fragmentStateAdapter=new ViewMangaStateAdapter(this,viewModel,manga);
        viewPager.setAdapter(fragmentStateAdapter);
        new TabLayoutMediator(binding.tabLayout,viewPager, (tab, position) -> tab.setText(tabs[position])).attach();
        viewPager.setUserInputEnabled(false);
    }


    private void setOnClickListeners() {
        String viewMore2="View More";
        String viewLess="View Less";
        binding.viewMore.setOnClickListener(v -> {
            if(viewMore){
                binding.description.setMaxLines(50);
                binding.viewMore.setText(viewLess);
            }else {
                binding.description.setMaxLines(5);
                binding.viewMore.setText(viewMore2);
            }
            viewMore=!viewMore;
        });

        binding.backButton2.setOnClickListener(v -> finish());
        binding.addToListButton.setOnClickListener(v -> addMangaBottomSheet.show(getSupportFragmentManager(),"AddAnimeBottomSheet"));
    }

    private void observeData() {
        viewModel.getMangaDetails().observe(ViewMangaActivity.this, manga -> {
            try {
                String posterPath;
                if(!manga.getPictures().isEmpty()){
                    posterPath=manga.getPictures().get(0).getMedium();
                    Picasso.get().load(posterPath).into(binding.backgroundPoster);
                }
                if(manga.getMainPicture()!=null){
                    MainPicture mainPicture=manga.getMainPicture();
                    List<MainPicture> pictures=manga.getPictures();
                    pictures.add(0,mainPicture);
                    Picasso.get().load(manga.getMainPicture().getLarge()).into(binding.posterView);
                    binding.posterView.setOnClickListener(v -> {
                        BaseApplication application=(BaseApplication) getApplicationContext();
                        application.setPictures(pictures);
                        startActivity(new Intent(ViewMangaActivity.this,PosterViewActivity.class));
                    });
                }
                String meanScore;
                float mean=manga.getMean();
                if(mean==0.0f){
                    meanScore="??/10";
                }else{
                    meanScore=mean+"/10";
                }
                binding.description.setText(manga.getSynopsis());
                binding.titleView.setText(manga.getTitle());
                binding.dateView.setText(manga.getStart_date());
                binding.ratingView.setText(meanScore);
                String ranked="#"+manga.getRank();
                String popularity="#"+manga.getPopularity();
                binding.popularityView.setText(popularity);
                binding.rankView.setText(ranked);
                addMangaBottomSheet.setData(manga);
                addGenres(manga.getGenres());
                setTabLayout(manga);
                binding.addToListButton.setText(manga.getMy_list_status().getStatus().toUpperCase(Locale.ROOT).replace("_"," "));
            }catch (Exception e){
                e.printStackTrace();
            }
            loadingDialog.stopLoading();
        });

        viewModel.getNoInternet().observe(ViewMangaActivity.this, NoInternet -> {
            if(NoInternet) {
                showNoInternetConnectionDialog();
            }
        });

    }

    public void showNoInternetConnectionDialog(){
        Dialog noInternetConnectionDialog= new Dialog(ViewMangaActivity.this);
        noInternetConnectionDialog.setContentView(R.layout.no_internet_connection_dialog);
        noInternetConnectionDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(ViewMangaActivity.this,R.drawable.dialog_background));
        noInternetConnectionDialog.setCancelable(false);
        Button okButton=noInternetConnectionDialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> noInternetConnectionDialog.dismiss());
        noInternetConnectionDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void setStatus(String status) {
        binding.addToListButton.setText(status);
        addedToList=true;
    }

    private void addGenres(List<Genre> genres) {
        ChipGroup chipGroup=binding.genres;
        ColorStateList colorStateList= ColorStateList.valueOf(ContextCompat.getColor(this,R.color.transparentBlue3));
        for(Genre genre: genres){
            Chip chip=new Chip(this);
            chip.setText(genre.getName());
            chip.setEnabled(false);
            chip.setChipBackgroundColor(colorStateList);
            chip.setTextAppearance(R.style.GenresChipStyle);
            chipGroup.addView(chip);
        }
    }


    @Override
    public void startLoading() {
        loadingDialog.startLoading();
    }

    @Override
    public void setResponseToObserve(MutableLiveData<Boolean> response) {
        response.observe(ViewMangaActivity.this, this::mangaListUpdateToast);
    }

    @Override
    public void observeDeleteResponse(MutableLiveData<Boolean> response) {
        response.observe(ViewMangaActivity.this, this::mangaListUpdateToast);
    }

    private void mangaListUpdateToast(boolean success){
        loadingDialog.stopLoading();
        if(success){
            Toast.makeText(ViewMangaActivity.this,"Manga List Updated Successfully",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ViewMangaActivity.this,"Something went wrong! \n Please try again",Toast.LENGTH_SHORT).show();
        }
    }


}