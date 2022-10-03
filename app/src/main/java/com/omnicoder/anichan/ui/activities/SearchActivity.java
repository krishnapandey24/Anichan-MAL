package com.omnicoder.anichan.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.tabs.TabLayout;
import com.omnicoder.anichan.adapters.SearchPageAdapter;
import com.omnicoder.anichan.utils.SearchPageComparator;
import com.omnicoder.anichan.viewModels.SearchViewModel;
import com.omnicoder.anichan.databinding.ActivitySearchBinding;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;


@AndroidEntryPoint
public class SearchActivity extends AppCompatActivity{
    ActivitySearchBinding binding;
    SearchViewModel viewModel;
    SearchPageAdapter adapter;
    CompositeDisposable compositeDisposable= new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel= new ViewModelProvider(this).get(SearchViewModel.class);
        setOnClickListeners();
        initRecyclerView();
        binding.searchEditText.requestFocus();
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(!binding.searchEditText.getText().toString().equals("")){
                    setSearchResults();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

    private void setOnClickListeners() {
        binding.searchButton.setOnClickListener(v -> {
            setSearchResults();
            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        });

        binding.backButton.setOnClickListener(v -> onBackPressed());
        binding.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_SEARCH){
                setSearchResults();
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return false;
        });
    }

    private void setSearchResults(){
        int isAnime=binding.tabLayout.getSelectedTabPosition();
        compositeDisposable.add(viewModel.getSearchResults(binding.searchEditText.getText().toString(),isAnime).subscribe(searchResults-> adapter.submitData(getLifecycle(),searchResults)));
    }


    public void initRecyclerView(){
        adapter=new SearchPageAdapter(new SearchPageComparator(),SearchActivity.this);
        binding.searchResults.setLayoutManager(new GridLayoutManager(SearchActivity.this,3));
        binding.searchResults.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(SearchActivity.this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding=null;
        compositeDisposable.dispose();
    }

}
