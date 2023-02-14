package com.omnicoder.anichan.ui.fragments.viewAnime;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnicoder.anichan.databinding.FragmentReviewsBinding;
import com.omnicoder.anichan.viewModels.ReviewsViewModel;

public class ReviewsFragment extends Fragment {

    private ReviewsViewModel mViewModel;
    int id;
    FragmentReviewsBinding binding;

    public ReviewsFragment(int id) {
        this.id=id;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= FragmentReviewsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReviewsViewModel.class);
        // TODO: Create Review fragment
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().requestLayout();
    }

}