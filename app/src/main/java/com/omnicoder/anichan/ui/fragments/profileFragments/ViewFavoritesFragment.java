package com.omnicoder.anichan.ui.fragments.profileFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.FriendsAdapter;
import com.omnicoder.anichan.adapters.JikanEntityAdapter;
import com.omnicoder.anichan.databinding.FragmentViewFavoritesBinding;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.ProfileViewModel;

import java.util.List;


public class ViewFavoritesFragment extends Fragment {
    FragmentViewFavoritesBinding binding;
    LoadingDialog loadingDialog;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentViewFavoritesBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog=new LoadingDialog(this,getContext());
        loadingDialog.startLoading();
        ProfileViewModel profileViewModel=new ViewModelProvider(this).get(ProfileViewModel.class);




    }
}