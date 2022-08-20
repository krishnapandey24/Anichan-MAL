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
import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.AddAnimeBottomSheet;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.CharactersFragment;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.StaffFragment;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.RelatedFragment;
import com.omnicoder.anichan.UI.Fragments.ViewAnimeFragments.SummaryFragment;
import com.omnicoder.anichan.Utils.Constants;
import com.omnicoder.anichan.ViewModels.ViewAnimeViewModel;
import com.omnicoder.anichan.databinding.ActivityViewAnimeBinding;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ViewAnimeActivity extends AppCompatActivity implements AddAnimeBottomSheet.AnimeAdded {
    ActivityViewAnimeBinding binding;
    ViewAnimeViewModel viewModel;
    boolean viewMore=true;
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
        viewModel.fetchAnimeDetails(intent.getIntExtra("id", 0));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        addAnimeBottomSheet=new AddAnimeBottomSheet();
        observeData();
        setOnClickListeners();
    }

    private void setTabLayout(Anime anime) {
        Fragment charactersFragment= new CharactersFragment(anime.getId(),viewModel);
        Fragment staffFragment=new StaffFragment(anime.getId(),viewModel);
        Fragment reviewsFragment= new RelatedFragment(anime.getId(),viewModel);

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
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,staffFragment).commit();
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
        viewModel.getAnimeDetails().observe(ViewAnimeActivity.this, anime -> {
            try {
                String posterPath;
                if(anime.getMainPicture()!=null){
                    Picasso.get().load(anime.getMainPicture().getLarge()).into(binding.posterView);
                }
                if(!anime.getPictures().isEmpty()){
                    posterPath=anime.getPictures().get(0).getMedium();
                    Picasso.get().load(posterPath).into(binding.backgroundPoster);
                }
                String meanScore;
                float mean=anime.getMean();
                if(mean==0.0f){
                    meanScore="??/10";
                }else{
                    meanScore=mean+"/10";
                }
                binding.description.setText(anime.getSynopsis());
                binding.titleView.setText(anime.getTitle());
                binding.formatView.setText(anime.getMedia_type());
                binding.dateView.setText(anime.getStart_date());
                binding.ratingView.setText(meanScore);
                if(anime.getStudios().isEmpty()){
                    binding.studioView.setText("?");
                }else{
                    binding.studioView.setText(anime.getStudios().get(0).getName());
                }
                summary=new SummaryFragment(anime,viewModel);
                if(anime.getMedia_type().equals(Constants.MOVIE)){
                    String duration=convertDuration(anime.getAverage_episode_duration());
                    String duration2="Duration";
                    binding.seasonAndDurationView.setText(duration);
                    binding.seasonAndDurationView2.setText(duration2);
                }else{
                    binding.seasonAndDurationView.setText(String.valueOf(anime.getNum_episodes()));
                }
                addAnimeBottomSheet.setData(anime);
                setTabLayout(anime);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,summary).commit();
                binding.addToListButton.setText(anime.getMy_list_status().getStatus().toUpperCase(Locale.ROOT).replace("_"," "));
            }catch (Exception e){
                e.printStackTrace();
                Log.d("tagg","Error: ignore "+e.getMessage());
            }
            binding.progressBar.setVisibility(View.GONE);
        });

        viewModel.getNoInternet().observe(ViewAnimeActivity.this, NoInternet -> {
            Log.d("tagg","No Internet connection"+NoInternet);
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

    private String convertDuration(int average_episode_duration) {
        return average_episode_duration/60%24 + "h " + average_episode_duration%60+"m ";
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
        response.observe(ViewAnimeActivity.this, aBoolean -> {
            Log.d("tagg","observer now");
            binding.progressBar.setVisibility(View.GONE);
            if(aBoolean){
                Toast.makeText(ViewAnimeActivity.this,"Anime List Updated Successfully",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(ViewAnimeActivity.this,"Something went wrong! \n Please try again",Toast.LENGTH_SHORT).show();
            }
        });
    }


}