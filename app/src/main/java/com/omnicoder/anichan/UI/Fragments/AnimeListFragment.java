package com.omnicoder.anichan.UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.omnicoder.anichan.Adapters.ViewPagerAdapter;
import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.UpdateAnimeBottomSheet;
import com.omnicoder.anichan.ViewModels.AnimeListViewModel;
import com.omnicoder.anichan.ViewModels.UpdateAnimeViewModel;
import com.omnicoder.anichan.databinding.AnimeListFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AnimeListFragment extends Fragment implements ViewPagerAdapter.PagerAdapterInterface {
    private AnimeListFragmentBinding binding;
    private AnimeListViewModel viewModel;
    private UpdateAnimeViewModel updateAnimeViewModel;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=AnimeListFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel= new ViewModelProvider(this).get(AnimeListViewModel.class);
        updateAnimeViewModel= new ViewModelProvider(this).get(UpdateAnimeViewModel.class);
//        viewModel.fetchUserAnimeList();
        context=getContext();
        setTabLayout();
        setOnClickListeners();
    }
    private void setTabLayout(){
        ViewPager2 viewPager2=binding.viewPager;
        String[] tabs = getResources().getStringArray(R.array.Statuses);
        viewPager2.setAdapter(new ViewPagerAdapter(context,tabs,viewModel,getViewLifecycleOwner(),AnimeListFragment.this));
        TabLayout tabLayout = binding.tabLayout2;
        new TabLayoutMediator(tabLayout, binding.viewPager, (tab, position) -> tab.setText(tabs[position])).attach();
    }

    private void setOnClickListeners(){
        binding.listSearchButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(AnimeListFragmentDirections.actionAnimeListFragmentToSearchListFragment()));
    }


    @Override
    public void updateAnime(UserAnime userAnime, int position) {
        updateAnimeViewModel.updateAnime(userAnime.getId(),userAnime.getStatus(),userAnime.isIs_rewatching(),userAnime.getScore(),userAnime.getNum_episodes_watched());
        updateAnimeViewModel.insertOrUpdateAnimeInList(userAnime);
    }

    @Override
    public void addEpisode(int id,int noOfEpisodesWatched) {
        updateAnimeViewModel.addEpisode(id,noOfEpisodesWatched);
    }

    @Override
    public void showEditor(UpdateAnimeBottomSheet updateAnimeBottomSheet) {
        updateAnimeBottomSheet.show(getChildFragmentManager(), "UpdateAnimeSheet");
    }

    @Override
    public void animeCompleted(int id,String name) {
        updateAnimeViewModel.animeCompleted(id);
        Toast.makeText(context,name+" Completed!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteAnime(int id) {
        updateAnimeViewModel.deleteAnime(id);
    }


}