package com.omnicoder.anichan.ui.activities;

import static com.omnicoder.anichan.utils.Constants.NSFW_TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.omnicoder.anichan.adapters.recyclerViews.AnimePageAdapter;
import com.omnicoder.anichan.adapters.recyclerViews.AnimePageAdapterPlain;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.utils.NodeComparator;
import com.omnicoder.anichan.viewModels.MangaRankingViewModel;
import com.omnicoder.anichan.databinding.ActivityMangaRankingBinding;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class MangaRankingActivity extends AppCompatActivity {
    private ActivityMangaRankingBinding binding;
    private MangaRankingViewModel viewModel;
    private String rankingType;
    AnimePageAdapter animePageAdapter;
    AnimePageAdapterPlain animePageAdapterPlain;
    private final CompositeDisposable compositeDisposable= new CompositeDisposable();
    int rankingTypeIndex;
    boolean three=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMangaRankingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel=new ViewModelProvider(this).get(MangaRankingViewModel.class);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String[] mangaRankingTypes;
        if(sharedPreferences.getBoolean(NSFW_TAG,false)){
            mangaRankingTypes= getResources().getStringArray(R.array.MangaRankingTypes_NSFW);
        }else{
            mangaRankingTypes= getResources().getStringArray(R.array.MangaRankingTypes);
        }
        rankingTypeIndex=getIntent().getIntExtra("mangaTypeIndex",0);
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(MangaRankingActivity.this,R.array.MangaRankingTypes,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.mangaRankingSpinner.setAdapter(arrayAdapter);
        binding.mangaRankingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setManga(binding.mangaRankingSpinner.getSelectedItem().toString(),three);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rankingType= mangaRankingTypes[rankingTypeIndex];
        animePageAdapter = new AnimePageAdapter(new NodeComparator(), MangaRankingActivity.this,false);
        animePageAdapterPlain= new AnimePageAdapterPlain(new NodeComparator(), MangaRankingActivity.this,false);
        setManga(rankingType,three);
        setupToolbar();
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

    public void setManga(String rankingType, boolean three){
        binding.progressBar.setVisibility(View.VISIBLE);
        if(three) {
            compositeDisposable.add(viewModel.getRanking(rankingType).subscribe(Manga ->{
                animePageAdapter.submitData(getLifecycle(), Manga);
                binding.progressBar.setVisibility(View.GONE);
            }));
            binding.animeView.setLayoutManager(new GridLayoutManager(MangaRankingActivity.this, 3));
            binding.animeView.setAdapter(animePageAdapter);
        }else {
            compositeDisposable.add(viewModel.getRanking(rankingType).subscribe(Manga -> {
                animePageAdapterPlain.submitData(getLifecycle(), Manga);
                binding.progressBar.setVisibility(View.GONE);
            }));
            binding.animeView.setLayoutManager(new LinearLayoutManager(MangaRankingActivity.this));
            binding.animeView.setAdapter(animePageAdapterPlain);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(MangaRankingActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
        compositeDisposable.dispose();
    }


    public Drawable getDrawableBasedOnThree(){
        if(three){
            return AppCompatResources.getDrawable(MangaRankingActivity.this,R.drawable.ic_baseline_view_module_24);
        }
        return AppCompatResources.getDrawable(MangaRankingActivity.this,R.drawable.ic_baseline_view_agenda_24);
    }

    private void setupToolbar(){
        binding.toolbar.setNavigationOnClickListener(v-> finish());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            three=!three;
            item.setIcon(getDrawableBasedOnThree());
            setManga(rankingType,three);
            return true;
        });
    }



























}