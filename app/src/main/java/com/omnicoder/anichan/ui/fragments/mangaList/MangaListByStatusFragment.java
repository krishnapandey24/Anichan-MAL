package com.omnicoder.anichan.ui.fragments.mangaList;

import android.os.Bundle;
import android.util.Log;
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

import com.omnicoder.anichan.adapters.recyclerViews.MangaListAdapter;
import com.omnicoder.anichan.database.UserManga;
import com.omnicoder.anichan.databinding.JustRecyclerViewBinding;
import com.omnicoder.anichan.ui.fragments.bottomSheets.UpdateMangaBottomSheet;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.MangaListViewModel;
import com.omnicoder.anichan.viewModels.UpdateMangaViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MangaListByStatusFragment extends Fragment implements MangaListAdapter.MyViewHolder.UpdateMangaList, UpdateMangaBottomSheet.UpdateManga {
    private MangaListViewModel viewModel;
    private UpdateMangaViewModel updateMangaViewModel;
    private JustRecyclerViewBinding binding;
    private LoadingDialog loadingDialog;
    private String mangaStatus;
    private MutableLiveData<List<UserManga>> listToObserve;
    private int sortBy = -1;

    public MangaListByStatusFragment() {
    }

    public MangaListByStatusFragment(String mangaStatus, MutableLiveData<List<UserManga>> listToObserve) {
        this.mangaStatus = mangaStatus;
        this.listToObserve = listToObserve;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireParentFragment()).get(MangaListViewModel.class);
        updateMangaViewModel = new ViewModelProvider(this).get(UpdateMangaViewModel.class);
        viewModel.fetchMangaByStatus(mangaStatus, sortBy,listToObserve);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = JustRecyclerViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog = new LoadingDialog(this, getContext());
        observeViewModels();
    }

    private void setupRecyclerView(List<UserManga> list) {
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        MangaListAdapter adapter = new MangaListAdapter(getContext(), list, this, this, 0);
        recyclerView.setAdapter(adapter);
    }

    private void observeViewModels() {
        listToObserve.observe(getViewLifecycleOwner(), mangaList -> {
            if (mangaList != null) {
                setupRecyclerView(mangaList);
            }
        });

        viewModel.getSortBy().observe(getViewLifecycleOwner(), sortBy -> {
            this.sortBy = sortBy;
            viewModel.fetchMangaByStatus(mangaStatus,sortBy,listToObserve);
        });

        observeAndShowToast(updateMangaViewModel.getUpdateMangaResponse());
    }

    @Override
    public void addChapter(int id, int noOfChaptersRead) {
        loadingDialog.startLoading();
        updateMangaViewModel.addChapter(id, noOfChaptersRead);
    }

    @Override
    public void showEditor(UpdateMangaBottomSheet updateMangaBottomSheet) {
        updateMangaBottomSheet.show(getChildFragmentManager(), "UpdateMangaSheet");
    }

    @Override
    public void mangaComplete(int id, String title) {
        updateMangaViewModel.mangaCompleted(id);
        Toast.makeText(getContext(), title + " Completed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fetchMore() {
        if (viewModel.getNextPage() != null) {
            Toast.makeText(getContext(), "Fetching more...", Toast.LENGTH_SHORT).show();
            viewModel.fetchNextPage();
        }
    }
    @Override
    public void updateManga(UserManga manga, int position) {
        loadingDialog.startLoading();
        updateMangaViewModel.updateManga(manga.getId(),manga.getStatus(), manga.isIs_rereading(),manga.getScore(), manga.getNoOfVolumesRead(), manga.getNoOfChaptersRead(),manga.getStartDate(),manga.getFinishDate());
        updateMangaViewModel.insertOrUpdateMangaInList(manga);
    }

    @Override
    public void deleteManga(int id) {
        loadingDialog.startLoading();
        observeAndShowToast(updateMangaViewModel.deleteResponse());
        updateMangaViewModel.deleteManga(id);
    }

    private void observeAndShowToast(MutableLiveData<Boolean> response) {
        response.observe(getViewLifecycleOwner(), success -> {
            loadingDialog.stopLoading();
            if (success) {
                Toast.makeText(getContext(), "Manga List Updated Successfully", Toast.LENGTH_SHORT).show();
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
