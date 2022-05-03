package com.omnicoder.anichan.UI.Activities;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.omnicoder.anichan.Adapters.SearchPageAdapter;
import com.omnicoder.anichan.Utils.SearchPageComparator;
import com.omnicoder.anichan.ViewModels.SearchViewModel;
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
        compositeDisposable.add(viewModel.getSearchResults(binding.searchEditText.getText().toString()).subscribe(searchResults-> adapter.submitData(getLifecycle(),searchResults)));

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
