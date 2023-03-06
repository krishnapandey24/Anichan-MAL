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
import com.omnicoder.anichan.adapters.stateAdapters.MangaListAdapter;
import com.omnicoder.anichan.databinding.FragmentMangaListBinding;
import com.omnicoder.anichan.ui.activities.SearchActivity;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.MangaListViewModel;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MangaListFragment extends Fragment {
    private FragmentMangaListBinding binding;
    private MangaListViewModel mangaListViewModel;
    private Dialog sortDialog=null;
    private String[] tabs;
    private LoadingDialog loadingDialog;
    private MangaListAdapter mangaListAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mangaListViewModel = new ViewModelProvider(this).get(MangaListViewModel.class);
        mangaListViewModel.fetchUserMangaList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMangaListBinding.inflate(inflater,container,false);
        tabs = getResources().getStringArray(R.array.MangaStatuses);
        setTabLayout();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog=new LoadingDialog(this,getContext());
        loadingDialog.startLoading();
        mangaListViewModel.getMangaListFetchedStatus().observe(getViewLifecycleOwner(), aBoolean -> {
            loadingDialog.stopLoading();
            Toast.makeText(getContext(), aBoolean ? "Fetched successfully": "Something went wrong", Toast.LENGTH_SHORT).show();
        });
        setupToolbar();
    }

    private void setTabLayout(){
        ViewPager2 viewPager=binding.viewPager;
        if(mangaListAdapter==null){
            mangaListAdapter=new MangaListAdapter(this);
        }
        viewPager.setAdapter(mangaListAdapter);
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
            sortDialog= new Dialog(getContext());
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
                mangaListViewModel.setSortBy(checkedRadioButton);
                sortDialog.dismiss();
            });
            cancelButton.setOnClickListener(v -> sortDialog.dismiss());
            sortDialog.getWindow().setAttributes(layoutParams);
        }
        sortDialog.show();


    }


}