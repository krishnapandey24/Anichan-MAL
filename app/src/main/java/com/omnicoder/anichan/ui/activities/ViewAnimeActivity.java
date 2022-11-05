package com.omnicoder.anichan.ui.activities;

import static com.omnicoder.anichan.utils.Constants.IMAGE_TYPE;
import static com.omnicoder.anichan.utils.Constants.POSTERS;
import static com.omnicoder.anichan.utils.Constants.VIEW_LESS;
import static com.omnicoder.anichan.utils.Constants.VIEW_MORE;

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
import com.omnicoder.anichan.adapters.stateAdapters.ViewAnimeStateAdapter;
import com.omnicoder.anichan.databinding.ActivityViewAnimeBinding;
import com.omnicoder.anichan.di.BaseApplication;
import com.omnicoder.anichan.models.animeResponse.Anime;
import com.omnicoder.anichan.models.responses.Genre;
import com.omnicoder.anichan.models.responses.MainPicture;
import com.omnicoder.anichan.ui.fragments.bottomSheets.AddAnimeBottomSheet;
import com.omnicoder.anichan.utils.Constants;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.ViewAnimeViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ViewAnimeActivity extends AppCompatActivity implements AddAnimeBottomSheet.AnimeAdded {
    ActivityViewAnimeBinding binding;
    ViewAnimeViewModel viewModel;
    boolean viewMore = true;
    AddAnimeBottomSheet addAnimeBottomSheet;
    boolean addedToList = false;
    LoadingDialog loadingDialog;
    FragmentStateAdapter fragmentStateAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoadingForActivity();
        binding = ActivityViewAnimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ViewAnimeViewModel.class);
        Intent intent = getIntent();
        viewModel.fetchAnimeDetails(intent.getIntExtra("id", 0));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        addAnimeBottomSheet = new AddAnimeBottomSheet();
        observeData();
        setOnClickListeners();
    }


    private void setTabLayout(ViewAnimeViewModel viewModel, Anime anime) {
        String[] tabs = getResources().getStringArray(R.array.ViewTabs);
        ViewPager2 viewPager;
        viewPager = binding.fragmentContainerView;
        if (fragmentStateAdapter == null) {
            fragmentStateAdapter = new ViewAnimeStateAdapter(this, viewModel, anime);
        }
        viewPager.setAdapter(fragmentStateAdapter);
        new TabLayoutMediator(binding.tabLayout, viewPager, (tab, position) -> tab.setText(tabs[position])).attach();
        viewPager.setUserInputEnabled(false);
    }


    private void setOnClickListeners() {
        View.OnClickListener onClickListener = v -> {
            if (viewMore) {
                binding.description.setMaxLines(50);
                binding.viewMore.setText(VIEW_LESS);
            } else {
                binding.description.setMaxLines(5);
                binding.viewMore.setText(VIEW_MORE);
            }
            viewMore = !viewMore;
        };
        binding.viewMore.setOnClickListener(onClickListener);
        binding.description.setOnClickListener(onClickListener);
        binding.backButton2.setOnClickListener(v -> finish());
        binding.menuButton.setOnClickListener(v -> {
            // TODO: 29-Oct-22 Complete this menu
        });
        binding.addToListButton.setOnClickListener(v -> addAnimeBottomSheet.show(getSupportFragmentManager(), "AddAnimeBottomSheet"));
    }

    private void observeData() {
        viewModel.getAnimeDetails().observe(ViewAnimeActivity.this, anime -> {
            try {
                String posterPath;
                if (!anime.getPictures().isEmpty()) {
                    posterPath = anime.getPictures().get(0).getMedium();
                    Picasso.get().load(posterPath).into(binding.backgroundPoster);
                }
                if (anime.getMainPicture() != null) {
                    MainPicture mainPicture = anime.getMainPicture();
                    Picasso.get().load(mainPicture.getLarge()).into(binding.posterView);
                    List<MainPicture> pictures = anime.getPictures();
                    pictures.add(0, mainPicture);
                    binding.posterView.setOnClickListener(v -> {
                        BaseApplication application = (BaseApplication) getApplicationContext();
                        application.setPictures(pictures);
                        Intent intent = new Intent(ViewAnimeActivity.this, PosterViewActivity.class);
                        intent.putExtra(IMAGE_TYPE, POSTERS);
                        startActivity(intent);
                    });
                }
                String meanScore;
                float mean = anime.getMean();
                if (mean == 0.0f) {
                    meanScore = "??/10";
                } else {
                    meanScore = mean + "/10";
                }
                binding.description.setText(anime.getSynopsis());
                binding.titleView.setText(anime.getTitle());
                binding.formatView.setText(anime.getMedia_type());
                binding.dateView.setText(anime.getStart_date());
                binding.ratingView.setText(meanScore);
                if (anime.getStudios().isEmpty()) {
                    binding.studioView.setText("?");
                } else {
                    binding.studioView.setText(anime.getStudios().get(0).getName());
                }
                if (anime.getMedia_type().equals(Constants.MOVIE)) {
                    String duration = convertDuration(anime.getAverage_episode_duration());
                    String duration2 = "Duration";
                    binding.seasonAndDurationView.setText(duration);
                    binding.seasonAndDurationView2.setText(duration2);
                } else {
                    String episodeCount= anime.getNum_episodes()==0 ? "?" : String.valueOf(anime.getNum_episodes());
                    binding.seasonAndDurationView.setText(episodeCount);
                }
                addGenres(anime.getGenres());
                addAnimeBottomSheet.setData(anime);
                setTabLayout(viewModel, anime);
                binding.addToListButton.setText(anime.getMy_list_status().getStatus().toUpperCase(Locale.ROOT).replace("_", " "));
            } catch (Exception e) {
                e.printStackTrace();
            }
            loadingDialog.stopLoading();
        });

        viewModel.getNoInternet().observe(ViewAnimeActivity.this, NoInternet -> {
            if (NoInternet) {
                showNoInternetConnectionDialog();
            }
        });

    }

    private void addGenres(List<Genre> genres) {
        ChipGroup chipGroup = binding.genres;
        ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.transparentBlue3));
        for (Genre genre : genres) {
            Chip chip = new Chip(this);
            chip.setText(genre.getName());
            chip.setEnabled(false);
            chip.setChipBackgroundColor(colorStateList);
            chip.setTextAppearance(R.style.GenresChipStyle);
            chipGroup.addView(chip);
        }
    }

    public void showNoInternetConnectionDialog() {
        Dialog noInternetConnectionDialog = new Dialog(ViewAnimeActivity.this);
        noInternetConnectionDialog.setContentView(R.layout.no_internet_connection_dialog);
        noInternetConnectionDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(ViewAnimeActivity.this, R.drawable.dialog_background));
        noInternetConnectionDialog.setCancelable(false);
        Button okButton = noInternetConnectionDialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(v -> noInternetConnectionDialog.dismiss());
        noInternetConnectionDialog.show();
    }

    private String convertDuration(int duration) {
        int min = (duration / 60) % 60;
        int hours = (duration / 60) / 60;
        return hours + "h" + min + "m";
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void setStatus(String status) {
        binding.addToListButton.setText(status);
        addedToList = true;
    }

    @Override
    public void startLoading() {
        loadingDialog.startLoading();
    }

    @Override
    public void setResponseToObserve(MutableLiveData<Boolean> response) {
        observeAndShowToast(response);
    }

    @Override
    public void observeDeleteAnime(MutableLiveData<Boolean> response) {
        observeAndShowToast(response);
    }

    private void observeAndShowToast(MutableLiveData<Boolean> response) {
        response.observe(ViewAnimeActivity.this, success -> {
            loadingDialog.stopLoading();
            if (success) {
                Toast.makeText(ViewAnimeActivity.this, "Anime List Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ViewAnimeActivity.this, "Something went wrong! \n Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.stopLoading();
    }
}