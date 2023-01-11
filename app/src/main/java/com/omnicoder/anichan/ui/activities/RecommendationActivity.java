package com.omnicoder.anichan.ui.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.AnimePageAdapter;
import com.omnicoder.anichan.adapters.recyclerViews.AnimePageAdapterPlain;
import com.omnicoder.anichan.databinding.ActivityRecommendationAnimeBinding;
import com.omnicoder.anichan.paging.SuggestedPagingSource;
import com.omnicoder.anichan.utils.NodeComparator;
import com.omnicoder.anichan.viewModels.AnimeChartViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class RecommendationActivity extends AppCompatActivity implements SuggestedPagingSource.ErrorHandler{
    private ActivityRecommendationAnimeBinding binding;
    private AnimeChartViewModel viewModel;
    AnimePageAdapter animePageAdapter;
    AnimePageAdapterPlain animePageAdapterPlain;
    private final CompositeDisposable compositeDisposable= new CompositeDisposable();
    boolean three=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecommendationAnimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel= new ViewModelProvider(this).get(AnimeChartViewModel.class);
        animePageAdapter = new AnimePageAdapter(new NodeComparator(), RecommendationActivity.this,true);
        animePageAdapterPlain= new AnimePageAdapterPlain(new NodeComparator(), RecommendationActivity.this,true);
        setAnime(three);
        setupToolbar();
    }

    public void setAnime(boolean three){
        binding.progressBar.setVisibility(View.VISIBLE);
        if(three) {
            compositeDisposable.add(viewModel.getSuggestedAnime(this).subscribe(Anime ->{
                animePageAdapter.submitData(getLifecycle(), Anime);
                binding.progressBar.setVisibility(View.GONE);
            }));
            binding.animeView.setLayoutManager(new GridLayoutManager(RecommendationActivity.this, 3));
            binding.animeView.setAdapter(animePageAdapter);
        }else {
            compositeDisposable.add(viewModel.getSuggestedAnime(this).subscribe(Anime -> {
                animePageAdapterPlain.submitData(getLifecycle(), Anime);
                binding.progressBar.setVisibility(View.GONE);
            }));
            binding.animeView.setLayoutManager(new LinearLayoutManager(RecommendationActivity.this));
            binding.animeView.setAdapter(animePageAdapterPlain);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(RecommendationActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
        compositeDisposable.dispose();
    }

    public Drawable getDrawableBasedOnThree(){
        if(three){
            return AppCompatResources.getDrawable(RecommendationActivity.this,R.drawable.ic_baseline_view_module_24);
        }
        return AppCompatResources.getDrawable(RecommendationActivity.this,R.drawable.ic_baseline_view_agenda_24);
    }

    private void setupToolbar(){
        binding.toolbar.setNavigationOnClickListener(v-> finish());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            three=!three;
            item.setIcon(getDrawableBasedOnThree());
            setAnime(three);
            return true;
        });
    }

    @Override
    public void error() {
        runOnUiThread(() -> Toast.makeText(RecommendationActivity.this,"Error in Connection or no data found",Toast.LENGTH_SHORT).show());
    }
}
