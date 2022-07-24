package com.omnicoder.anichan.UI.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.Adapters.AnimePageAdapter;
import com.omnicoder.anichan.Adapters.AnimePageAdapterPlain;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.Utils.AnimeComparator;
import com.omnicoder.anichan.ViewModels.AnimeChartViewModel;
import com.omnicoder.anichan.databinding.ActivitySeasonBinding;

import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class SeasonActivity extends AppCompatActivity {
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
        viewModel= new ViewModelProvider(this).get(AnimeChartViewModel.class);
        setSpinners();
        animePageAdapter = new AnimePageAdapter(new AnimeComparator(), SeasonActivity.this);
        animePageAdapterPlain= new AnimePageAdapterPlain(new AnimeComparator(), SeasonActivity.this);
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
        if(three) {
            compositeDisposable.add(viewModel.getSeason(year, season).subscribe(Anime -> animePageAdapter.submitData(getLifecycle(), Anime)));
            binding.seasonView.setLayoutManager(new GridLayoutManager(SeasonActivity.this, 3));
            binding.seasonView.setAdapter(animePageAdapter);
        }else {
            compositeDisposable.add(viewModel.getSeason(year, season).subscribe(Anime -> animePageAdapterPlain.submitData(getLifecycle(), Anime)));
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


    private void setSpinners(){
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
        int month = c.get(Calendar.MONTH) + 1;
        int yearPosition=yearArrayAdapter.getPosition(String.valueOf(year));
        int seasonPosition;
        if(month<4){
            seasonPosition=0;
        }else if(month<7){
            seasonPosition=1;
        }else if(month<9){
            seasonPosition=2;
        }else{
            seasonPosition=3;
        }
        binding.seasonSpinner.setSelection(seasonPosition);
        binding.yearSpinner.setSelection(yearPosition);
    }


    public Drawable getDrawableBasedOnThree(){
        if(three){
            return AppCompatResources.getDrawable(SeasonActivity.this,R.drawable.ic_baseline_view_module_24);
        }
        return AppCompatResources.getDrawable(SeasonActivity.this,R.drawable.ic_baseline_view_agenda_24);
    }

}