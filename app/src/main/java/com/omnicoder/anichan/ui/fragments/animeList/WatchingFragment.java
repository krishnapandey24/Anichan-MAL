package com.omnicoder.anichan.ui.fragments.animeList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.adapters.recyclerViews.AnimeListAdapter;
import com.omnicoder.anichan.database.UserAnime;
import com.omnicoder.anichan.databinding.AnimeListFragmentsBinding;
import com.omnicoder.anichan.ui.fragments.bottomSheets.UpdateAnimeBottomSheet;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.AnimeListViewModel;
import com.omnicoder.anichan.viewModels.UpdateAnimeViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WatchingFragment extends Fragment implements AnimeListAdapter.MyViewHolder.UpdateAnimeList, UpdateAnimeBottomSheet.UpdateAnime{
    private AnimeListViewModel viewModel;
    private UpdateAnimeViewModel updateAnimeViewModel;
    private AnimeListFragmentsBinding binding;
    private LoadingDialog loadingDialog;
    int sortBy=-1;


    public WatchingFragment(){}

    public static WatchingFragment newInstance(){
        return new WatchingFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireParentFragment()).get(AnimeListViewModel.class);
        updateAnimeViewModel=new ViewModelProvider(this).get(UpdateAnimeViewModel.class);
        viewModel.fetchWatching(sortBy);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= AnimeListFragmentsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog=new LoadingDialog(this,getContext());
        RecyclerView recyclerView=binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        viewModel.getWatching().observe(getViewLifecycleOwner(), animeList-> {
            if(!animeList.isEmpty()){
                AnimeListAdapter adapter = new AnimeListAdapter(getContext(), animeList, this, this,0);
                recyclerView.setAdapter(adapter);
            }
        });
        viewModel.getSortBy().observe(getViewLifecycleOwner(),sortBy -> viewModel.fetchWatching(sortBy));
    }

    @Override
    public void addEpisode(int id, int noOfEpisodesWatched) {
        loadingDialog.startLoading();
        updateAnimeViewModel.addEpisode(id,noOfEpisodesWatched);
    }

    @Override
    public void showEditor(UpdateAnimeBottomSheet updateAnimeBottomSheet) {
        updateAnimeBottomSheet.show(getChildFragmentManager(), "UpdateAnimeSheet");
    }

    @Override
    public void animeComplete(int id, String title) {
        updateAnimeViewModel.animeCompleted(id);
        Toast.makeText(getContext(),title+" Completed!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fetchMore() {
        if(viewModel.getNextPage()!=null){
            Toast.makeText(getContext(),"Fetching more...",Toast.LENGTH_SHORT).show();
            viewModel.fetchNextPage();
        }
    }

    @Override
    public void updateAnime(UserAnime anime, int viewPagerPosition) {
        loadingDialog.startLoading();
        updateAnimeViewModel.updateAnime(anime.getId(),anime.getStatus(),anime.isIs_rewatching(),anime.getScore(),anime.getNum_episodes_watched(),anime.getStartDate(),anime.getFinishDate());
        updateAnimeViewModel.insertOrUpdateAnimeInList(anime);
        observeAndShowToast(updateAnimeViewModel.getUpdateAnimeResponse());
    }

    @Override
    public void deleteAnime(int id) {
        loadingDialog.startLoading();
        observeAndShowToast(updateAnimeViewModel.deleteResponse());
        updateAnimeViewModel.deleteAnime(id);
    }

    private void observeAndShowToast(MutableLiveData<Boolean> response) {
        response.observe(getViewLifecycleOwner(), success -> {
            loadingDialog.stopLoading();
            if (success) {
                Toast.makeText(getContext(), "Anime  List Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Something went wrong! \n Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.getRoot().requestLayout();
    }



}
