package com.omnicoder.anichan.ui.fragments.animeListFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.AnimeListAdapter;
import com.omnicoder.anichan.databinding.FragmentWatchingBinding;
import com.omnicoder.anichan.databinding.ProgressBarBinding;
import com.omnicoder.anichan.viewModels.AnimeListViewModel;


public class WatchingFragment extends Fragment {
    WatchingFragment watchingFragment;
    FragmentWatchingBinding binding;
    AnimeListViewModel viewModel;
    AnimeListAdapter animeListAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    public WatchingFragment(){}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentWatchingBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel= new ViewModelProvider(this).get(AnimeListViewModel.class);
    }
}