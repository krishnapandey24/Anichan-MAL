package com.omnicoder.anichan.UI.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.omnicoder.anichan.Adapters.SeasonPageAdapter;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.SeasonAnimeBottomSheet;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.SeasonSelector;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.YearSelector;
import com.omnicoder.anichan.Utils.AnimeComparator;
import com.omnicoder.anichan.Utils.AnimePlainComparator;
import com.omnicoder.anichan.ViewModels.AnimeChartViewModel;
import com.omnicoder.anichan.databinding.ActivitySeasonBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class SeasonActivity extends AppCompatActivity implements SeasonAnimeBottomSheet.SeasonBottomSheet, SeasonSelector.SeasonSheet, YearSelector.YearSheet {
    public static final String ONE="1";
    ActivitySeasonBinding binding;
    ArrayList<Integer> years;
    AnimeChartViewModel viewModel;
    CompositeDisposable compositeDisposable=new CompositeDisposable();
    SeasonPageAdapter animePageAdapter;
    AnimePageAdapterPlain animePageAdapterPlain;
    SeasonAnimeBottomSheet seasonAnimeBottomSheet;
    String animeType;
    int season;
    boolean three=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeasonBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Intent intent= getIntent();
        season=intent.getIntExtra("Season",3);
        viewModel= new ViewModelProvider(this).get(AnimeChartViewModel.class);
        years= new ArrayList<>();
        animePageAdapter = new SeasonPageAdapter(new AnimeComparator(),SeasonActivity.this);
        animePageAdapterPlain = new AnimePageAdapterPlain(new AnimePlainComparator(),SeasonActivity.this);
        int[] years2=viewModel.getYears();
        years.add(years2[0]);
        years.add(years2[1]);
        years.add(years2[2]);
        animeType=season+ONE;
        setAnime(animeType);
        setOnClickListeners();
        createSpinners();
        setSpinners();

    }

    private void setOnClickListeners() {
        binding.backButton.setOnClickListener(view -> onBackPressed());
        binding.menuButton.setOnClickListener(v -> {
            if(seasonAnimeBottomSheet==null){
                seasonAnimeBottomSheet= new SeasonAnimeBottomSheet();
            }
            seasonAnimeBottomSheet.show(getSupportFragmentManager(),"SeasonAnimeBottomSheet");
        });
        binding.itemLayout.setOnClickListener(v->changeItemLayout());
    }

    private void changeItemLayout() {
        three=!three;
        binding.itemLayout.setImageDrawable(getDrawableBasedOnThree());
        setAnime(animeType);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(SeasonActivity.this);

    }


    public void setAnime(String seasonType){
        animeType=seasonType;
        if(three) {
            compositeDisposable.add(viewModel.getSeasonPage(seasonType).subscribe(Anime -> animePageAdapter.submitData(getLifecycle(), Anime)));
            binding.seasonView.setLayoutManager(new GridLayoutManager(SeasonActivity.this, 3));
            binding.seasonView.setAdapter(animePageAdapter);
        }else {
            compositeDisposable.add(viewModel.getSeasonPagePlain(seasonType).subscribe(Anime -> animePageAdapterPlain.submitData(getLifecycle(), Anime)));
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

    @Override
    public void sortAnimeList(String sortBy, String format) {
        if(three){
            setPersonalizedAnime(sortBy,format);
        }else {
            setPersonalizedAnimePlain(sortBy,format);
        }
    }

    private void setPersonalizedAnime(String sortBy, String format) {
        AnimePageAdapter animePageAdapter = new AnimePageAdapter(new AnimeComparator(),SeasonActivity.this);
        compositeDisposable.add(viewModel.getPersonalizedAnime(sortBy,format).subscribe(Anime -> animePageAdapter.submitData(getLifecycle(),Anime)));
        binding.seasonView.setLayoutManager(new GridLayoutManager(SeasonActivity.this,3));
        binding.seasonView.setAdapter(animePageAdapter);
    }

    private void setPersonalizedAnimePlain(String sortBy, String format) {
        AnimePageAdapterPlain animePageAdapterPlain = new AnimePageAdapterPlain(new AnimePlainComparator(),SeasonActivity.this);
        compositeDisposable.add(viewModel.getPersonalizedAnimePlain(sortBy,format).subscribe(Anime -> animePageAdapterPlain.submitData(getLifecycle(),Anime)));
        binding.seasonView.setLayoutManager(new LinearLayoutManager(SeasonActivity.this));
        binding.seasonView.setAdapter(animePageAdapterPlain);
    }

    private void createSpinners() {
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


        ArrayAdapter<Integer> yearAdapter= new ArrayAdapter<Integer>(SeasonActivity.this,R.layout.drop_down,years){
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
    }

    private void setSpinners(){
        binding.seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year=binding.yearSpinner.getSelectedItemPosition();
                switch (position){
                    case 0:
                        setAnime("Winter"+year);
                        break;
                    case 1:
                        setAnime("Spring"+year);
                        break;
                    case 2:
                        setAnime("Summer"+year);
                        break;
                    case 3:
                        setAnime("Fall"+year);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String season=binding.seasonSpinner.getSelectedItem().toString();
                setAnime(season+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.seasonSpinner.setSelection(season);
        binding.yearSpinner.setSelection(1);

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