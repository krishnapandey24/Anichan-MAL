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
import com.omnicoder.anichan.databinding.ExploreMangaBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreMangaFragment extends Fragment{

    private ExploreMangaBinding binding;
    private ExploreViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=ExploreMangaBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel= new ViewModelProvider(this).get(ExploreViewModel.class);
        observeData();
        viewModel.fetchTopManga();
        viewModel.fetchTopManhwa();
        viewModel.fetchTopManhua();
        if (Build.VERSION.SDK_INT >= 25) {
            SnapHelper pagerSnapHelper= new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(binding.mangaView);
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
                }
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void setOnClickListeners() {
        binding.topMangaTitle.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToMangaRankingActivity action= ExploreFragmentDirections.actionExploreFragmentToMangaRankingActivity(1);
            Navigation.findNavController(v).navigate(action);
        });

        binding.topManhwaTitle.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToMangaRankingActivity action= ExploreFragmentDirections.actionExploreFragmentToMangaRankingActivity(5);
            Navigation.findNavController(v).navigate(action);
        });

        binding.topManhuaTitle.setOnClickListener(v -> {
            ExploreFragmentDirections.ActionExploreFragmentToMangaRankingActivity action= ExploreFragmentDirections.actionExploreFragmentToMangaRankingActivity(6);
            Navigation.findNavController(v).navigate(action);
        });

    }

    private void observeData(){
        LifecycleOwner lifecycleOwner= getViewLifecycleOwner();
        viewModel.getTopManga().observe(lifecycleOwner, this::setTopManga);
        viewModel.getTopManhwa().observe(lifecycleOwner,this::setTopManhwa);
        viewModel.getTopManhua().observe(lifecycleOwner,this::setTopManhua);
    }


    public void setTopManga(List<Data> exploreViews){
        RecyclerView recyclerView= binding.mangaView;
        TrendingViewPagerAdapter adapter = new TrendingViewPagerAdapter(getContext(), exploreViews,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        addOnItemTouchListener(recyclerView);

    }


    public void setTopManhwa(List<Data> exploreViews){
        RecyclerView recyclerView= binding.manhwaView;
        Season2Adapter seasonAdapter2 = new Season2Adapter(getContext(), exploreViews,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(seasonAdapter2);
        addOnItemTouchListener(recyclerView);

    }

    public void setTopManhua(List<Data> exploreViews){
        RecyclerView recyclerView= binding.manhuaView;
        Top100Adapter seasonAdapter2 = new Top100Adapter(getContext(), exploreViews,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(seasonAdapter2);
        addOnItemTouchListener(recyclerView);

    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

}