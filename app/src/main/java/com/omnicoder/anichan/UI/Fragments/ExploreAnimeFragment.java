package com.omnicoder.anichan.UI.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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

import com.omnicoder.anichan.Adapters.Season2Adapter;
import com.omnicoder.anichan.Adapters.Top100Adapter;
import com.omnicoder.anichan.Adapters.TrendingViewPagerAdapter;
import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.ViewModels.ExploreViewModel;
import com.omnicoder.anichan.databinding.ExploreAnimeFragmentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreAnimeFragment extends Fragment{
    private ExploreAnimeFragmentBinding binding;
    private ExploreViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=ExploreAnimeFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel= new ViewModelProvider(this).get(ExploreViewModel.class);
        observeData();
        viewModel.fetchTrending();
        viewModel.fetchSuggestions();
        viewModel.fetchTopUpcoming();
        if (Build.VERSION.SDK_INT >= 25) {
            SnapHelper pagerSnapHelper= new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(binding.trendingView);
        }
        setOnClickListeners();

    }
    private void addOnItemTouchListener(RecyclerView recyclerView){
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action=e.getAction();
                if(recyclerView.canScrollHorizontally(RecyclerView.FOCUS_FORWARD)){
                    if(action==MotionEvent.ACTION_MOVE){
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                }else{
                    if(action==MotionEvent.ACTION_MOVE){
                        rv.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    recyclerView.removeOnItemTouchListener(this);
                    return true;
                }            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void setOnClickListeners() {
        binding.todayTitle.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action= ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(1);
            Navigation.findNavController(v).navigate(action);
        });

        binding.upcomingTitle.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action= ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(2);
            Navigation.findNavController(v).navigate(action);
        });

        binding.recommendationTitle.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToViewAnimeActivity action= ExploreFragmentDirections.actionExploreFragmentToViewAnimeActivity(7);
            Navigation.findNavController(v).navigate(action);
        });



        binding.animeBySeason.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action= ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(0,"Winter");
            Navigation.findNavController(v).navigate(action);
        });


        binding.viewWinter.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToSeasonActivity action= ExploreFragmentDirections.actionExploreFragmentToSeasonActivity(0,"Winter");
            Navigation.findNavController(v).navigate(action);
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

    }

    private void observeData(){
        LifecycleOwner lifecycleOwner= getViewLifecycleOwner();
        viewModel.getTrendingAnime().observe(lifecycleOwner, this::setTrending);
        viewModel.getTopUpcomingAnime().observe(lifecycleOwner,this::setTopUpcoming);
        viewModel.getRecommendation().observe(lifecycleOwner,this::setRecommendations);
    }


    public void setTrending(List<Data> exploreViews){
        RecyclerView recyclerView= binding.trendingView;
        TrendingViewPagerAdapter adapter = new TrendingViewPagerAdapter(getContext(), exploreViews,true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        addOnItemTouchListener(recyclerView);

    }


    public void setTopUpcoming(List<Data> exploreViews){
        Season2Adapter seasonAdapter2 = new Season2Adapter(getContext(), exploreViews,true);
        binding.upcomingView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.upcomingView.setAdapter(seasonAdapter2);
        addOnItemTouchListener(binding.upcomingView);

    }

    public void setRecommendations(List<Data> exploreViews){
        Top100Adapter seasonAdapter2 = new Top100Adapter(getContext(), exploreViews,true);
        binding.recommendationView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        binding.recommendationView.setAdapter(seasonAdapter2);
        addOnItemTouchListener(binding.recommendationView);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

}