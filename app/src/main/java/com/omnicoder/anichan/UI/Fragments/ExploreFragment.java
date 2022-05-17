package com.omnicoder.anichan.UI.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.omnicoder.anichan.Adapters.AllTimePopularAdapter;
import com.omnicoder.anichan.Adapters.Season2Adapter;
import com.omnicoder.anichan.Adapters.Top100Adapter;
import com.omnicoder.anichan.Adapters.TrendingViewPagerAdapter;
import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.UI.Activities.MainActivity;
import com.omnicoder.anichan.ViewModels.ExploreViewModel;
import com.omnicoder.anichan.databinding.ExploreFragmentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreFragment extends Fragment{

    private ExploreFragmentBinding binding;
    private ExploreViewModel viewModel;
    private final Context context= getContext();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=ExploreFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel= new ViewModelProvider(this).get(ExploreViewModel.class);
        observeData();
        viewModel.fetchTrending();
        viewModel.fetchTop100();
        viewModel.fetchPopular();
        viewModel.fetchTopUpcoming();
        if (Build.VERSION.SDK_INT >= 25) {
            SnapHelper pagerSnapHelper= new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(binding.trendingView);
        }
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.searchEditText.setOnClickListener(v -> Navigation.findNavController(v).navigate(ExploreFragmentDirections.actionExploreFragmentToSearchActivity()));

        binding.searchButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(ExploreFragmentDirections.actionExploreFragmentToSearchActivity()));
        binding.scanButton.setOnClickListener(v -> {
            Toast.makeText(getContext(),"Started to fetch Data",Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(),"Done",Toast.LENGTH_SHORT).show();
        });

        binding.viewTrending.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action= ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(1);
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewTopUpcoming.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action= ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(2);
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewAllTimePopular.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action= ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(7);
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewTop100.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action= ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(0);
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewWinter.setOnClickListener(view->{
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action= ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(0,"Winter");
            Navigation.findNavController(view).navigate(action);
        });

        binding.viewSpring.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action= ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(1,"Spring");
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewSummer.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action= ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(2,"Summer");
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewFall.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action= ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(3,"Fall");
            Navigation.findNavController(v).navigate(action);
        });

        binding.viewAnimeSeasons.setOnClickListener(v->{
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action= ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(0,"Spring");
            Navigation.findNavController(v).navigate(action);
        });

    }

    private void observeData(){
        LifecycleOwner lifecycleOwner= getViewLifecycleOwner();
        viewModel.getTrendingAnime().observe(lifecycleOwner, this::setTrending);
        viewModel.getTop100Anime().observe(lifecycleOwner,this::setTop100);
        viewModel.getTopUpcomingAnime().observe(lifecycleOwner,this::setTopUpcoming);
        viewModel.getAllTimePopularAnime().observe(lifecycleOwner,this::setAllTimePopular);
        viewModel.getStartLoading().observe(lifecycleOwner, startLoading -> {
            if(startLoading){
                binding.progressBarViewStub.inflate().setVisibility(View.VISIBLE);
            }else {
                binding.progressBarViewStub.setVisibility(View.GONE);
            }
        });
        viewModel.getNoInternet().observe(lifecycleOwner, NoInternet -> {
            if(NoInternet) {
                ((MainActivity) requireActivity()).showNoInternetConnectionDialog();
            }
        });
    }


    public void setTrending(List<Data> exploreViews){
        RecyclerView recyclerView= binding.trendingView;
        TrendingViewPagerAdapter adapter = new TrendingViewPagerAdapter(getContext(), exploreViews);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);

    }

    public void setAllTimePopular(List<Data> exploreViews){
        AllTimePopularAdapter adapter = new AllTimePopularAdapter(getContext(), exploreViews);
        binding.allTimePopularView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.allTimePopularView.setAdapter(adapter);

    }

    public void setTopUpcoming(List<Data> exploreViews){
        Season2Adapter seasonAdapter2 = new Season2Adapter(getContext(), exploreViews);
        binding.upcomingView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.upcomingView.setAdapter(seasonAdapter2);
    }


    public void setTop100(List<Data> exploreViews){
        Top100Adapter top100Adapter = new Top100Adapter(getContext(), exploreViews);
        binding.top100View.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        binding.top100View.setAdapter(top100Adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

}