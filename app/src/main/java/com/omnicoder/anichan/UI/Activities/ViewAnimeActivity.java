package com.omnicoder.anichan.UI.Activities;

import static com.omnicoder.anichan.Utils.Constants.IMAGE_URL;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.omnicoder.anichan.Models.Season;
import com.omnicoder.anichan.Models.ViewAnime;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.AddAnimeBottomSheet;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.CastFragment;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.SeasonDetailsFragment;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.StudioFragment;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.SummaryFragment;
import com.omnicoder.anichan.ViewModels.ViewAnimeViewModel;
import com.omnicoder.anichan.databinding.ActivityViewAnimeBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ViewAnimeActivity extends AppCompatActivity implements AddAnimeBottomSheet.AnimeAdded {
    ActivityViewAnimeBinding binding;
    ViewAnimeViewModel viewModel;
    boolean viewMore=true;
    static final String duration="Duration";
    String media_type,title,format;
    boolean single,main;
    int seasonNo,totalEpisodes;
    SummaryFragment summary;
    AddAnimeBottomSheet addAnimeBottomSheet;
    boolean addedToList=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAnimeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(ViewAnimeViewModel.class);
        Intent intent = getIntent();
        media_type = intent.getStringExtra("media_type");
        single = intent.getBooleanExtra("single", false);
        main = intent.getBooleanExtra("main", true);
        seasonNo = intent.getIntExtra("seasonNo", 0);
        format=intent.getStringExtra("format");
        viewModel.fetchAnimeDetails(media_type, intent.getIntExtra("id", 0), "alternative_titles,videos,credits,external_ids");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        addAnimeBottomSheet=new AddAnimeBottomSheet();
        observeData();
        setOnClickListeners();
    }

    private void setTabLayout(ViewAnime viewAnime) {
        Fragment Seasons= new SeasonDetailsFragment(viewAnime);
        Fragment Cast=new CastFragment(viewAnime,true);
        Fragment Crew=new CastFragment(viewAnime,false);
        Fragment Studios= new StudioFragment(viewAnime);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position= tab.getPosition();
                switch (position){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,summary).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,Seasons).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,Cast).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,Crew).commit();
                        break;
                    case 4:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,Studios).commit();
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

    private void setTabLayoutForMovie(ViewAnime viewAnime) {
        binding.tabLayout.removeTabAt(1);
        Fragment Cast=new CastFragment(viewAnime,true);
        Fragment Crew=new CastFragment(viewAnime,false);
        Fragment Studios= new StudioFragment(viewAnime);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position= tab.getPosition();
                Log.d("tagg","Heyy"+position);
                switch (position){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,summary).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,Cast).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,Crew).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,Studios).commit();
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
        binding.addToListButton.setOnClickListener(v -> addAnimeBottomSheet.show(getSupportFragmentManager(),"AddAnimeBottomSheet"));
    }

    private void observeData() {
        viewModel.getAnimeDetails().observe(ViewAnimeActivity.this, viewAnime -> {
            try {
                viewAnime.setMedia_typeAndSingle(media_type, single, seasonNo, main);
                Log.d("tagg", "" + viewAnime.getTitle() + viewAnime.getMedia_type() + viewAnime.getVote_average() + viewAnime.getFirstProductionCompany());
                title=viewAnime.getTitle();
                totalEpisodes=viewAnime.getNumber_of_episodesInt();
                Picasso.get().load(IMAGE_URL + viewAnime.getBackdrop_path()).into(binding.backgroundPoster);
                List<Season> seasonList= viewAnime.getSeasons();
                if (single) {
                    Season season = viewAnime.getSeason();
                    viewAnime.setSeason(season);
                    summary = new SummaryFragment(viewAnime);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, summary).commit();
                    setTabLayout(viewAnime);
                    String name = title + "\n" + season.getName();
                    String overview = season.getOverview();
                    String overview2 = overview == null || overview.length() < 1 ? viewAnime.getOverview() : overview;
                    String posterPath=IMAGE_URL + season.getPoster_path();
                    Picasso.get().load(posterPath).into(binding.posterView);
                    binding.description.setText(overview2);
                    binding.titleView.setText(name);
                    binding.dateView.setText(season.getAirYear());
                    binding.description.setText(viewAnime.getOverview());
                    binding.seasonAndDurationView.setText(season.getEpisode_count());
                    addAnimeBottomSheet.setData(title,viewAnime.getNumber_of_episodesInt(),viewAnime.getId(),viewAnime.getSeasonNo(),posterPath,media_type,media_type,addedToList,viewAnime.getS(),viewAnime.getStatus(),viewAnime.getListStatus());
                    addAnimeBottomSheet.setSeasons(seasonList);
                } else {
                    summary = new SummaryFragment(viewAnime);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, summary).commit();
                    String posterPath=IMAGE_URL + viewAnime.getPoster_path();
                    Picasso.get().load(posterPath).into(binding.posterView);
                    binding.titleView.setText(title);
                    binding.dateView.setText(viewAnime.getFirst_air_Year());
                    binding.description.setText(viewAnime.getOverview());
                    if (media_type.equals("tv")) {
                        setTabLayout(viewAnime);
                        binding.seasonAndDurationView.setText(viewAnime.getNumber_of_episodes());
                        addAnimeBottomSheet.setData(title,totalEpisodes,viewAnime.getId(),viewAnime.getSeasonNo(),posterPath,format,media_type,addedToList,viewAnime.getS(),viewAnime.getStatus(),viewAnime.getListStatus());
                        addAnimeBottomSheet.setSeasons(seasonList);
                    } else {
                        setTabLayoutForMovie(viewAnime);
                        binding.seasonAndDurationView.setText(viewAnime.getRuntime());
                        binding.seasonAndDurationView2.setText(duration);
                        addAnimeBottomSheet.setData(title,1,viewAnime.getId(),viewAnime.getSeasonNo(),posterPath,format,media_type,addedToList,false,viewAnime.getStatus(),viewAnime.getListStatus());
                    }
                }
                binding.ratingView.setText(viewAnime.getVote_average());
                binding.studioView.setText(viewAnime.getFirstProductionCompany());
                binding.formatView.setText(viewAnime.getMedia_type());

            }catch (Exception e){
                e.printStackTrace();
                Log.d("tagg","Error: "+e.getMessage());
            }
        });

        viewModel.getNoInternet().observe(ViewAnimeActivity.this, NoInternet -> {
            Log.d("tagg","NO Internet connection"+NoInternet);
            if(NoInternet) {
                showNoInternetConnectionDialog();
            }
        });
    }

    public void showNoInternetConnectionDialog(){
        Dialog noInternetConnectionDialog= new Dialog(ViewAnimeActivity.this);
        noInternetConnectionDialog.setContentView(R.layout.no_internet_connection_dialog);
        noInternetConnectionDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(ViewAnimeActivity.this,R.drawable.dialog_background));
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
}