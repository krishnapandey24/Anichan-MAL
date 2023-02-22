package com.omnicoder.anichan.ui.activities;

import android.content.Intent;
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
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.AnimePageAdapter;
import com.omnicoder.anichan.adapters.recyclerViews.AnimePageAdapterPlain;
import com.omnicoder.anichan.databinding.ActivitySeasonBinding;
import com.omnicoder.anichan.paging.SeasonPagingSource;
import com.omnicoder.anichan.utils.NodeComparator;
import com.omnicoder.anichan.viewModels.AnimeChartViewModel;

import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class SeasonActivity extends AppCompatActivity implements SeasonPagingSource.ErrorHandler{
    ActivitySeasonBinding binding;
    AnimeChartViewModel viewModel;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    AnimePageAdapter animePageAdapter;
    AnimePageAdapterPlain animePageAdapterPlain;
    boolean three=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeasonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent= getIntent();
        viewModel= new ViewModelProvider(this).get(AnimeChartViewModel.class);
        setSpinners(intent.getIntExtra("SeasonIndex",0));
        animePageAdapter = new AnimePageAdapter(new NodeComparator(), SeasonActivity.this,true);
        animePageAdapterPlain= new AnimePageAdapterPlain(new NodeComparator(), SeasonActivity.this,true);
//        setAnime(selectedSeason,currentYear);
        setupToolbar();
    }



    private void setupToolbar(){
        binding.toolbar.setNavigationOnClickListener(v-> finish());
        binding.toolbar.setOnMenuItemClickListener(item -> {
            three=!three;
            item.setIcon(getDrawableBasedOnThree());
            setAnime(binding.yearSpinner.getSelectedItem().toString(),binding.seasonSpinner.getSelectedItem().toString());
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(SeasonActivity.this);
    }


    public void setAnime(String year,String season){
        binding.progressBar.setVisibility(View.VISIBLE);
        if(three) {
            compositeDisposable.add(viewModel.getSeason(year, season, this).subscribe(Anime -> {
                animePageAdapter.submitData(getLifecycle(), Anime);
                binding.progressBar.setVisibility(View.GONE);
            }));
            binding.seasonView.setLayoutManager(new GridLayoutManager(SeasonActivity.this, 3));
            binding.seasonView.setAdapter(animePageAdapter);
        }else {
            compositeDisposable.add(viewModel.getSeason(year, season,this).subscribe(Anime -> {
                animePageAdapterPlain.submitData(getLifecycle(), Anime);
                binding.progressBar.setVisibility(View.GONE);

            }));
            binding.seasonView.setLayoutManager(new LinearLayoutManager(SeasonActivity.this));
            binding.seasonView.setAdapter(animePageAdapterPlain);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
        compositeDisposable.dispose();
    }


    private void setSpinners(int seasonIndex){
        ArrayAdapter<CharSequence> seasonArrayAdapter= ArrayAdapter.createFromResource(SeasonActivity.this,R.array.Seasons, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> yearArrayAdapter= ArrayAdapter.createFromResource(SeasonActivity.this,R.array.Years, android.R.layout.simple_spinner_item);
        seasonArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.seasonSpinner.setAdapter(seasonArrayAdapter);
        binding.yearSpinner.setAdapter(yearArrayAdapter);
        binding.seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setAnime(binding.yearSpinner.getSelectedItem().toString(),binding.seasonSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setAnime(binding.yearSpinner.getSelectedItem().toString(),binding.seasonSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int yearPosition=yearArrayAdapter.getPosition(String.valueOf(year));
        binding.seasonSpinner.setSelection(seasonIndex);
        binding.yearSpinner.setSelection(yearPosition);
    }


    public Drawable getDrawableBasedOnThree(){
        if(three){
            return AppCompatResources.getDrawable(SeasonActivity.this,R.drawable.ic_baseline_view_module_24);
        }
        return AppCompatResources.getDrawable(SeasonActivity.this,R.drawable.ic_baseline_view_agenda_24);
    }

    @Override
    public void error() {
        runOnUiThread(() -> Toast.makeText(SeasonActivity.this,"Error in Connection or no data found",Toast.LENGTH_SHORT).show());
    }
}