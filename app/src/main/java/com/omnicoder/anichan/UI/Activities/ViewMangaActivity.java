package com.omnicoder.anichan.UI.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.omnicoder.anichan.Models.MangaResponse.Manga;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.AddMangaBottomSheet;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.CharactersFragment;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.MangaSummaryFragment;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.RelatedFragment;
import com.omnicoder.anichan.ViewModels.MangaDetailsViewModel;
import com.omnicoder.anichan.databinding.ActivityViewMangaBinding;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ViewMangaActivity extends AppCompatActivity implements AddMangaBottomSheet.MangaAdded {
    ActivityViewMangaBinding binding;
    MangaDetailsViewModel viewModel;
    boolean viewMore=true;
    MangaSummaryFragment summary;
    AddMangaBottomSheet addMangaBottomSheet;
    boolean addedToList=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Fragment charactersFragment= new CharactersFragment(manga.getId(),viewModel);
        Fragment reviewsFragment= new RelatedFragment(manga.getId(),viewModel);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position= tab.getPosition();
                switch (position){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,summary).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,charactersFragment).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,reviewsFragment).commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                if(manga.getMainPicture()!=null){
                    Picasso.get().load(manga.getMainPicture().getLarge()).into(binding.posterView);
                }
                if(!manga.getPictures().isEmpty()){
                    posterPath=manga.getPictures().get(0).getMedium();
                    Picasso.get().load(posterPath).into(binding.backgroundPoster);
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
                summary=new MangaSummaryFragment(manga);
                addMangaBottomSheet.setData(manga);
                setTabLayout(manga);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,summary).commit();
                binding.addToListButton.setText(manga.getMy_list_status().getStatus().toUpperCase(Locale.ROOT).replace("_"," "));
            }catch (Exception e){
                e.printStackTrace();
            }
            binding.progressBar.setVisibility(View.GONE);
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

    @Override
    public void startLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
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
        binding.progressBar.setVisibility(View.GONE);
        if(success){
            Toast.makeText(ViewMangaActivity.this,"Manga List Updated Successfully",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ViewMangaActivity.this,"Something went wrong! \n Please try again",Toast.LENGTH_SHORT).show();
        }
    }


}