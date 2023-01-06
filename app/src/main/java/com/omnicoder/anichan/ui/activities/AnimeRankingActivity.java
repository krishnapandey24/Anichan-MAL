package com.omnicoder.anichan.ui.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.omnicoder.anichan.adapters.recyclerViews.AnimePageAdapter;
import com.omnicoder.anichan.adapters.recyclerViews.AnimePageAdapterPlain;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.paging.RankingPagingSource;
import com.omnicoder.anichan.utils.NodeComparator;
import com.omnicoder.anichan.viewModels.AnimeChartViewModel;
import com.omnicoder.anichan.databinding.ActivityChartAnimeBinding;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class AnimeRankingActivity extends AppCompatActivity implements RankingPagingSource.ErrorHandler{
    private ActivityChartAnimeBinding binding;
    private AnimeChartViewModel viewModel;
    private String rankingType;
    AnimePageAdapter animePageAdapter;
    AnimePageAdapterPlain animePageAdapterPlain;
    private final CompositeDisposable compositeDisposable= new CompositeDisposable();
    int rankingTypeIndex;
    boolean three=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChartAnimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel= new ViewModelProvider(this).get(AnimeChartViewModel.class);
        rankingTypeIndex=getIntent().getIntExtra("animeTypeIndex",0);
        String[] rankingTypes = getResources().getStringArray(R.array.RankingTypes);
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(AnimeRankingActivity.this,R.array.RankingTypes,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.animeRankingSpinner.setAdapter(arrayAdapter);
        binding.animeRankingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setAnime(binding.animeRankingSpinner.getSelectedItem().toString(),three);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rankingType= rankingTypes[rankingTypeIndex];
        animePageAdapter = new AnimePageAdapter(new NodeComparator(), AnimeRankingActivity.this,true);
        animePageAdapterPlain= new AnimePageAdapterPlain(new NodeComparator(), AnimeRankingActivity.this,true);
        setAnime(rankingType,three);
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

    public void setAnime(String rankingType, boolean three){
        binding.progressBar.setVisibility(View.VISIBLE);
        if(three) {
            compositeDisposable.add(viewModel.getRanking(rankingType,this).subscribe(Anime ->{
                animePageAdapter.submitData(getLifecycle(), Anime);
                binding.progressBar.setVisibility(View.GONE);
            }));
            binding.animeView.setLayoutManager(new GridLayoutManager(AnimeRankingActivity.this, 3));
            binding.animeView.setAdapter(animePageAdapter);
        }else {
            compositeDisposable.add(viewModel.getRanking(rankingType,this).subscribe(Anime -> {
                animePageAdapterPlain.submitData(getLifecycle(), Anime);
                binding.progressBar.setVisibility(View.GONE);
            }));
            binding.animeView.setLayoutManager(new LinearLayoutManager(AnimeRankingActivity.this));
            binding.animeView.setAdapter(animePageAdapterPlain);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(AnimeRankingActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
        compositeDisposable.dispose();
    }

    public Drawable getDrawableBasedOnThree(){
        if(three){
            return AppCompatResources.getDrawable(AnimeRankingActivity.this,R.drawable.ic_baseline_view_module_24);
        }
        return AppCompatResources.getDrawable(AnimeRankingActivity.this,R.drawable.ic_baseline_view_agenda_24);
    }

    private void setupToolbar(){
        binding.toolbar.setNavigationOnClickListener(v-> finish());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            three=!three;
            item.setIcon(getDrawableBasedOnThree());
            setAnime(rankingType,three);
            return true;
        });
    }

    @Override
    public void error() {
        runOnUiThread(() -> Toast.makeText(AnimeRankingActivity.this,"Error in Connection or no data found",Toast.LENGTH_SHORT).show());
    }
}
