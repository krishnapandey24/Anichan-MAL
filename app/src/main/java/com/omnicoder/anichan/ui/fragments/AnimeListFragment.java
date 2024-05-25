package com.omnicoder.anichan.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textview.MaterialTextView;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.stateAdapters.AnimeListStateAdapter;
import com.omnicoder.anichan.databinding.AnimeListFragmentBinding;
import com.omnicoder.anichan.ui.activities.SearchActivity;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.AnimeListViewModel;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AnimeListFragment extends Fragment {
    private AnimeListFragmentBinding binding;
    private AnimeListViewModel viewModel;
    private Dialog sortDialog=null;
    private String[] tabs;
    private LoadingDialog loadingDialog;
    private AnimeListStateAdapter animeListStateAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel= new ViewModelProvider(this).get(AnimeListViewModel.class);
        viewModel.fetchUserAnimeList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=AnimeListFragmentBinding.inflate(inflater,container,false);
        tabs = getResources().getStringArray(R.array.Statuses);
        setTabLayout();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog=new LoadingDialog(this,getContext());
        loadingDialog.startLoading();
        viewModel.getAnimeListFetchedStatus().observe(getViewLifecycleOwner(), aBoolean -> {
            loadingDialog.stopLoading();
            if(!aBoolean) Toast.makeText(getContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
        });
        setupToolbar();
    }
    private void setTabLayout(){
        ViewPager2 viewPager=binding.viewPager;
        if(animeListStateAdapter==null){
            animeListStateAdapter=new AnimeListStateAdapter(this);
        }
        viewPager.setAdapter(animeListStateAdapter);
        new TabLayoutMediator(binding.tabLayout,viewPager,(tab, position) ->tab.setText(tabs[position])).attach();
    }



    private void setupToolbar(){
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()== R.id.sort){
                launchSortDialog();
            }else{
                Intent intent=new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
            return true;
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void launchSortDialog() {
        if(sortDialog==null){
            sortDialog= new Dialog(requireContext());
            sortDialog.setContentView(R.layout.list_sort_dialog);
            sortDialog.setCancelable(true);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(sortDialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            MaterialTextView okButton=sortDialog.findViewById(R.id.ok);
            RadioGroup radioGroup=sortDialog.findViewById(R.id.radioGroup);
            MaterialTextView cancelButton=sortDialog.findViewById(R.id.cancel);
            okButton.setOnClickListener(v -> {
                int checkedRadioButton=radioGroup.getCheckedRadioButtonId();
                int sortBy;
                if(checkedRadioButton==R.id.title){
                    sortBy=1;
                } else if(checkedRadioButton==R.id.score){
                    sortBy=2;
                }else{
                    sortBy=0;
                }
                viewModel.setSortBy(sortBy);
                sortDialog.dismiss();
            });
            cancelButton.setOnClickListener(v -> sortDialog.dismiss());
            sortDialog.getWindow().setAttributes(layoutParams);
        }
        sortDialog.show();
    }





}