package com.omnicoder.anichan.UI.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.Adapters.AnimePageAdapter;
import com.omnicoder.anichan.Adapters.AnimePageAdapterPlain;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.SeasonAnimeBottomSheet;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.SeasonSelector;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.YearSelector;
import com.omnicoder.anichan.Utils.AnimeComparator;
import com.omnicoder.anichan.ViewModels.AnimeChartViewModel;
import com.omnicoder.anichan.databinding.ActivitySeasonBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class SeasonActivity extends AppCompatActivity implements SeasonSelector.SeasonSheet, YearSelector.YearSheet {
    ActivitySeasonBinding binding;
    AnimeChartViewModel viewModel;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    AnimePageAdapter animePageAdapter;
    AnimePageAdapterPlain animePageAdapterPlain;
    SeasonAnimeBottomSheet seasonAnimeBottomSheet;
    boolean three=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeasonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent= getIntent();
        int selectedSeasonIndex=intent.getIntExtra("SeasonIndex",0);
        String selectedSeason=intent.getStringExtra("Season");
        viewModel= new ViewModelProvider(this).get(AnimeChartViewModel.class);
        String currentYear=String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        animePageAdapter = new AnimePageAdapter(new AnimeComparator(), SeasonActivity.this);
        animePageAdapterPlain= new AnimePageAdapterPlain(new AnimeComparator(), SeasonActivity.this);
        setAnime(selectedSeason,currentYear);
        setOnClickListeners();
        setSpinners(selectedSeasonIndex,currentYear);
    }

    private void setOnClickListeners() {
        binding.backButton.setOnClickListener(view -> onBackPressed());
        binding.itemLayout.setOnClickListener(v->changeItemLayout());
    }

    private void changeItemLayout() {
        three=!three;
        binding.itemLayout.setImageDrawable(getDrawableBasedOnThree());
        setAnime(binding.yearSpinner.getSelectedItem().toString(),binding.seasonSpinner.getSelectedItem().toString());
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


    private void setSpinners(int selectedSeasonIndex,String currentYear){
        ArrayAdapter<String> seasonAdapter= new ArrayAdapter<String>(SeasonActivity.this,R.layout.drop_down,getResources().getStringArray(R.array.Seasons)){
            @Override
            public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, null, parent);
                view.setBackgroundColor(getResources().getColor(R.color.white));
                TextView textView= view.findViewById(R.id.spinner_text);
                textView.setTextColor(getResources().getColor(R.color.textColor));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                textView.setPadding(18,18,18,18);
                return view;
            }
        };

        String[] yearArray= getResources().getStringArray(R.array.Years);

        ArrayAdapter<String> yearAdapter= new ArrayAdapter<String>(SeasonActivity.this,R.layout.drop_down,yearArray){
            @Override
            public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, null, parent);
                view.setBackgroundColor(getResources().getColor(R.color.white));
                TextView textView= view.findViewById(R.id.spinner_text);
                textView.setTextColor(getResources().getColor(R.color.textColor));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                textView.setPadding(18,18,18,18);
                return view;
            }
        };

        binding.seasonSpinner.setAdapter(seasonAdapter);
        binding.yearSpinner.setAdapter(yearAdapter);

        int selectedIndex=yearArray.length-1;
        while(!currentYear.equals(yearArray[selectedIndex])){
            selectedIndex--;
        }
        binding.yearSpinner.setSelection(selectedIndex);
        binding.seasonSpinner.setSelection(selectedSeasonIndex);


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



    }



    @Override
    public void setSeason(int animeTypeIndex) {


    }

    @Override
    public void setYear(int animeTypeIndex) {

    }

    public Drawable getDrawableBasedOnThree(){
        if(three){
            return AppCompatResources.getDrawable(SeasonActivity.this,R.drawable.ic_baseline_view_module_24);
        }
        return AppCompatResources.getDrawable(SeasonActivity.this,R.drawable.ic_baseline_view_agenda_24);
    }



}