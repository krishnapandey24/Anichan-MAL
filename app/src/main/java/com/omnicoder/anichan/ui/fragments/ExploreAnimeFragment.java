package com.omnicoder.anichan.ui.fragments;

import static com.omnicoder.anichan.utils.AdsConstants.NATIVE_AD_UNIT_ID;
import static com.omnicoder.anichan.utils.Constants.LIST_SPACE_HEIGHT;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.AnimeAdapter;
import com.omnicoder.anichan.adapters.recyclerViews.SeasonAdapter;
import com.omnicoder.anichan.adapters.viewpagers.TrendingViewPagerAdapter;
import com.omnicoder.anichan.databinding.ExploreAnimeFragmentBinding;
import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.utils.ListItemHorizontalDecor;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.ExploreViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreAnimeFragment extends Fragment {
    private ExploreAnimeFragmentBinding binding;
    private ExploreViewModel viewModel;
    private LoadingDialog loadingDialog;
    private int fetchCount=0;
    @Inject
    SharedPreferences sharedPreferences;
    NativeAd nativeAd;
    Dialog noInternetConnectionDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        viewModel.fetchTrending();
        viewModel.fetchSuggestions();
        viewModel.fetchTopUpcoming();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ExploreAnimeFragmentBinding.inflate(inflater, container, false);
        binding.trendingView.addItemDecoration(new ListItemHorizontalDecor(LIST_SPACE_HEIGHT));
        binding.trendingView.setHasFixedSize(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog = new LoadingDialog(this, getContext());
        loadingDialog.startLoading();
        observeData();
        initializeGoogleAdmob();
        if (Build.VERSION.SDK_INT >= 25) {
            SnapHelper pagerSnapHelper = new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(binding.trendingView);
        }
        setOnClickListeners();

    }

    private void initializeGoogleAdmob(){
        MobileAds.initialize(requireContext(),initializationStatus -> {
            AdLoader adLoader = new AdLoader.Builder(requireContext(), NATIVE_AD_UNIT_ID).forNativeAd(nativeAd -> {
                if (isAdded() && requireActivity().isDestroyed()) {
                    nativeAd.destroy();
                    return;
                }
                if(this.nativeAd!=null){
                    this.nativeAd.destroy();
                }

                this.nativeAd=nativeAd;
                binding.adView.setNativeAd(nativeAd);
            }).build();
            AdRequest nativeAdRequest = new AdRequest.Builder().build();
            adLoader.loadAd(nativeAdRequest);
        });
    }


    private void setOnClickListeners() {
        binding.todayTitle.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action = ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(1);
            Navigation.findNavController(v).navigate(action);
        });

        binding.upcomingTitle.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action = ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(2);
            Navigation.findNavController(v).navigate(action);
        });

        binding.recommendationTitle.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action = ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(7);
            Navigation.findNavController(v).navigate(action);
        });


        binding.animeBySeason.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action = ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(0, "Winter");
            Navigation.findNavController(v).navigate(action);
        });


        binding.viewWinter.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action = ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(0, "Winter");
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewSpring.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action = ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(1, "Spring");
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewSummer.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action = ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(2, "Summer");
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewFall.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action = ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(3, "Fall");
            Navigation.findNavController(v).navigate(action);
        });

    }

    private void observeData() {
        LifecycleOwner lifecycleOwner = getViewLifecycleOwner();
        viewModel.getNoInternet().observe(lifecycleOwner, aBoolean -> {
            Log.d("tagg","moved: "+aBoolean);
            if(aBoolean){
                showNoInternetConnectionDialog();
            }
        });
        viewModel.getTrendingAnime().observe(lifecycleOwner, this::setTrending);
        viewModel.getTopUpcomingAnime().observe(lifecycleOwner, this::setTopUpcoming);
        viewModel.getRecommendation().observe(lifecycleOwner, this::setRecommendations);
    }


    public void setTrending(List<Data> exploreViews) {
        fetchCount++;
        RecyclerView recyclerView = binding.trendingView;
        TrendingViewPagerAdapter adapter = new TrendingViewPagerAdapter(getContext(), exploreViews, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        stopLoading();
    }


    public void setTopUpcoming(List<Data> exploreViews) {
        fetchCount++;
        SeasonAdapter seasonAdapter2 = new SeasonAdapter(getContext(), exploreViews, true);
        binding.upcomingView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.upcomingView.setAdapter(seasonAdapter2);
        stopLoading();
    }

    public void setRecommendations(List<Data> exploreViews) {
        fetchCount++;
        AnimeAdapter seasonAdapter2 = new AnimeAdapter(getContext(), exploreViews, true);
        binding.recommendationView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recommendationView.setAdapter(seasonAdapter2);
        stopLoading();
    }

    private void stopLoading(){
        if(fetchCount>2){
            loadingDialog.stopLoading();
        }
    }

    public void showNoInternetConnectionDialog() {
        if(noInternetConnectionDialog==null){
            noInternetConnectionDialog = new Dialog(getContext());
            noInternetConnectionDialog.setContentView(R.layout.no_internet_connection_dialog);
            noInternetConnectionDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.dialog_background));
            noInternetConnectionDialog.setCancelable(false);
            Button okButton = noInternetConnectionDialog.findViewById(R.id.okButton);
            okButton.setOnClickListener(v -> {
                noInternetConnectionDialog.dismiss();
                viewModel.fetchTrending();
                viewModel.fetchSuggestions();
                viewModel.fetchTopUpcoming();

            });
        }
        if(!noInternetConnectionDialog.isShowing()){
            noInternetConnectionDialog.show();
        }

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (nativeAd != null) {
            nativeAd.destroy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        loadingDialog.stopLoading();
    }
}