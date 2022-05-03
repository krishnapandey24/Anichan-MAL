package com.omnicoder.anichan.UI.Activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.Adapters.AnimePageAdapter;
import com.omnicoder.anichan.Adapters.AnimePageAdapterPlain;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.AnimeChartBottomSheet;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.ViewAnimeBottomSheet;
import com.omnicoder.anichan.Utils.AnimeComparator;
import com.omnicoder.anichan.Utils.AnimePlainComparator;
import com.omnicoder.anichan.ViewModels.AnimeChartViewModel;
import com.omnicoder.anichan.databinding.ActivityChartAnimeBinding;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class AnimeChartActivity extends AppCompatActivity implements ViewAnimeBottomSheet.MenuBottomSheet, AnimeChartBottomSheet.AnimeChartSheet {
    private ActivityChartAnimeBinding binding;
    private AnimeChartViewModel viewModel;
    private String[] animeTypes;
    private String animeType;
    private final CompositeDisposable compositeDisposable= new CompositeDisposable();
    ViewAnimeBottomSheet menuBottomSheet;
    AnimeChartBottomSheet animeChartBottomSheet;
    int animeTypeIndex;
    boolean three=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChartAnimeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel= new ViewModelProvider(this).get(AnimeChartViewModel.class);
        animeTypeIndex=getIntent().getIntExtra("animeTypeIndex",0);
        animeTypes=getResources().getStringArray(R.array.AnimeTypes);
        binding.topChartSelector.setText(animeTypes[animeTypeIndex]);
        animeType= animeTypes[animeTypeIndex];
        setAnime(animeType,three);
        setOnClickListeners();
        Log.d("tagg","First"+three);

    }

    private void setOnClickListeners() {
        binding.backButton.setOnClickListener(v -> onBackPressed());
        binding.menuButton.setOnClickListener(v -> showMenuBottomSheet());
        binding.topChartSelector.setOnClickListener(v -> showAnimeChartBottomSheet());
        binding.itemLayout.setOnClickListener(v -> changeItemLayout());
    }

    private void changeItemLayout() {
        three=!three;
        binding.itemLayout.setImageDrawable(getDrawableBasedOnThree());
        setAnime(animeType,three);
        Log.d("tagg","Now "+three);
    }

    private void showAnimeChartBottomSheet() {
        if(animeChartBottomSheet==null){
            animeChartBottomSheet=new AnimeChartBottomSheet();
        }
        animeChartBottomSheet.setIndex(animeTypeIndex);
        animeChartBottomSheet.show(getSupportFragmentManager(),"AnimeChartSheet");
    }

    private void showMenuBottomSheet() {
        if(menuBottomSheet==null){
            menuBottomSheet=new ViewAnimeBottomSheet();
        }
        menuBottomSheet.show(getSupportFragmentManager(),"ViewAnimeBottomSheet");
    }

    public void setAnime(String animeType, boolean three){
        if(three) {
            AnimePageAdapter animePageAdapter = new AnimePageAdapter(new AnimeComparator(), AnimeChartActivity.this);
            compositeDisposable.add(viewModel.getAnimePage(animeType).subscribe(Anime -> animePageAdapter.submitData(getLifecycle(), Anime)));
            binding.animeView.setLayoutManager(new GridLayoutManager(AnimeChartActivity.this, 3));
            binding.animeView.setAdapter(animePageAdapter);
        }else {
            AnimePageAdapterPlain animePageAdapterPlain= new AnimePageAdapterPlain(new AnimePlainComparator(), AnimeChartActivity.this);
            compositeDisposable.add(viewModel.getAnimePagePlain(animeType).subscribe(Anime -> animePageAdapterPlain.submitData(getLifecycle(), Anime)));
            binding.animeView.setLayoutManager(new LinearLayoutManager(AnimeChartActivity.this));
            binding.animeView.setAdapter(animePageAdapterPlain);
        }
    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(AnimeChartActivity.this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
        compositeDisposable.dispose();
    }


    @Override
    public void sortAnimeList(String sortBy,String airingStatus,String format) {
        if (three) {
            setPersonalizedAnime(sortBy, airingStatus, format);
        } else {
            setPersonalizedAnimePlain(sortBy, airingStatus, format);
        }
    }

    private void setPersonalizedAnime(String sortBy, String airingStatus, String format) {
        AnimePageAdapter animePageAdapter = new AnimePageAdapter(new AnimeComparator(),AnimeChartActivity.this);
        compositeDisposable.add(viewModel.getPersonalizedAnime(sortBy,airingStatus,format).subscribe(Anime -> animePageAdapter.submitData(getLifecycle(),Anime)));
        binding.animeView.setLayoutManager(new GridLayoutManager(AnimeChartActivity.this,3));
        binding.animeView.setAdapter(animePageAdapter);
    }

    private void setPersonalizedAnimePlain(String sortBy, String airingStatus, String format) {
        AnimePageAdapterPlain animePageAdapterPlain = new AnimePageAdapterPlain(new AnimePlainComparator(),AnimeChartActivity.this);
        compositeDisposable.add(viewModel.getPersonalizedAnimePlain(sortBy,airingStatus,format).subscribe(Anime -> animePageAdapterPlain.submitData(getLifecycle(),Anime)));
        binding.animeView.setLayoutManager(new LinearLayoutManager(AnimeChartActivity.this));
        binding.animeView.setAdapter(animePageAdapterPlain);
    }

    @Override
    public void changeList(int animeTypeIndex) {
        if(animeTypeIndex<4){
            animeType=animeTypes[animeTypeIndex];
            setAnime(animeType,three);
            Log.d("tagg","Anime Type"+animeTypeIndex);
            this.animeTypeIndex=animeTypeIndex;
            binding.topChartSelector.setText(animeType);
        }
        else {
            showMenuBottomSheet();
        }

    }

    public Drawable getDrawableBasedOnThree(){
        if(three){
            return AppCompatResources.getDrawable(AnimeChartActivity.this,R.drawable.ic_baseline_view_module_24);
        }
        return AppCompatResources.getDrawable(AnimeChartActivity.this,R.drawable.ic_baseline_view_agenda_24);
    }

}
