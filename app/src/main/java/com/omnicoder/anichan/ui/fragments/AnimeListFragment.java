package com.omnicoder.anichan.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textview.MaterialTextView;
import com.omnicoder.anichan.adapters.AnimeViewPagerAdapter;
import com.omnicoder.anichan.database.UserAnime;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.fragments.bottomSheets.UpdateAnimeBottomSheet;
import com.omnicoder.anichan.viewModels.AnimeListViewModel;
import com.omnicoder.anichan.viewModels.UpdateAnimeViewModel;
import com.omnicoder.anichan.databinding.AnimeListFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AnimeListFragment extends Fragment implements AnimeViewPagerAdapter.AnimePagerAdapterInterface {
    private AnimeListFragmentBinding binding;
    private AnimeListViewModel viewModel;
    private UpdateAnimeViewModel updateAnimeViewModel;
    private Dialog sortDialog=null;
    private String[] tabs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=AnimeListFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel= new ViewModelProvider(this).get(AnimeListViewModel.class);
        viewModel.fetchUserAnimeList();
        viewModel.getAnimeListFetchedStatus().observe(getViewLifecycleOwner(), aBoolean -> binding.progressBar.setVisibility(View.GONE));
        updateAnimeViewModel= new ViewModelProvider(this).get(UpdateAnimeViewModel.class);
        tabs = getResources().getStringArray(R.array.Statuses);
        setTabLayout();
        setupToolbar();
    }
    private void setTabLayout(){
        binding.viewPager.setAdapter(new AnimeViewPagerAdapter(getContext(),tabs,viewModel,getViewLifecycleOwner(),AnimeListFragment.this,"t_id"));
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(tabs[position])).attach();
    }

    @Override
    public void updateAnime(UserAnime userAnime, int position) {
        binding.progressBar.setVisibility(View.VISIBLE);
        updateAnimeViewModel.updateAnime(userAnime.getId(),userAnime.getStatus(),userAnime.isIs_rewatching(),userAnime.getScore(),userAnime.getNum_episodes_watched());
        updateAnimeViewModel.insertOrUpdateAnimeInList(userAnime);
        observeAndShowToast(updateAnimeViewModel.getUpdateAnimeResponse());
    }

    @Override
    public void addEpisode(int id,int noOfEpisodesWatched) {
        binding.progressBar.setVisibility(View.VISIBLE);
        updateAnimeViewModel.addEpisode(id,noOfEpisodesWatched);
        observeAndShowToast(updateAnimeViewModel.getResponse());

    }

    @Override
    public void showEditor(UpdateAnimeBottomSheet updateAnimeBottomSheet) {
        updateAnimeBottomSheet.show(getChildFragmentManager(), "UpdateAnimeSheet");
    }

    @Override
    public void animeCompleted(int id,String name) {
        updateAnimeViewModel.animeCompleted(id);
        Toast.makeText(getContext(),name+" Completed!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteAnime(int id) {
        binding.progressBar.setVisibility(View.VISIBLE);
        observeAndShowToast(updateAnimeViewModel.deleteResponse());
        updateAnimeViewModel.deleteAnime(id);
    }

    private void setupToolbar(){
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()== R.id.sort){
                launchSortDialog();
            }else{
                Navigation.findNavController(binding.toolbar).navigate(AnimeListFragmentDirections.actionAnimeListFragmentToSearchListFragment(true));
            }
            return true;
        });
    }

    @SuppressLint("NonConstantResourceId")
    private void launchSortDialog() {
        if(sortDialog==null){
            sortDialog= new Dialog(getContext());
            sortDialog.setContentView(R.layout.list_sort_dialog);
            sortDialog.setCancelable(true);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(sortDialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            MaterialTextView okButton=sortDialog.findViewById(R.id.ok);
            RadioGroup radioGroup=sortDialog.findViewById(R.id.radioGroup);
            MaterialTextView cancelButton=sortDialog.findViewById(R.id.cancel);
            okButton.setOnClickListener(v -> {
                int checkedRadioButton=radioGroup.getCheckedRadioButtonId();
                String sortBy;
                switch (checkedRadioButton){
                    case R.id.title:
                        sortBy="title";
                        break;
                    case R.id.score:
                        sortBy="score";
                        break;
                    default:
                        sortBy="t_id";
                        break;
                }
                binding.viewPager.setAdapter(new AnimeViewPagerAdapter(getContext(),tabs,viewModel,getViewLifecycleOwner(),AnimeListFragment.this,sortBy));
                sortDialog.dismiss();
            });
            cancelButton.setOnClickListener(v -> sortDialog.dismiss());
            sortDialog.getWindow().setAttributes(layoutParams);
        }
        sortDialog.show();

    }

    private void observeAndShowToast(MutableLiveData<Boolean> response) {
        response.observe(getViewLifecycleOwner(), success -> {
            binding.progressBar.setVisibility(View.GONE);
            if (success) {
                Toast.makeText(getContext(), "Anime  List Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Something went wrong! \n Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }




}