package com.omnicoder.anichan.UI.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
        updateAnimeViewModel= new ViewModelProvider(this).get(UpdateAnimeViewModel.class);
        tabs = getResources().getStringArray(R.array.Statuses);
        context=getContext();
        setTabLayout();
        setupToolbar();
    }
    private void setTabLayout(){
        binding.viewPager.setAdapter(new ViewPagerAdapter(context,tabs,viewModel,getViewLifecycleOwner(),AnimeListFragment.this,"t_id"));
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(tabs[position])).attach();
    }

    @Override
    public void updateAnime(UserAnime userAnime, int position) {
        binding.progressBar2.setVisibility(View.VISIBLE);
        updateAnimeViewModel.updateAnime(userAnime.getId(),userAnime.getStatus(),userAnime.isIs_rewatching(),userAnime.getScore(),userAnime.getNum_episodes_watched());
        updateAnimeViewModel.insertOrUpdateAnimeInList(userAnime);
        updateAnimeViewModel.getUpdateAnimeResponse().observe(getViewLifecycleOwner(),success -> {
            if(success){
                Toast.makeText(context,"Anime Updated Successfully!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void addEpisode(int id,int noOfEpisodesWatched) {
        binding.progressBar2.setVisibility(View.VISIBLE);
        updateAnimeViewModel.addEpisode(id,noOfEpisodesWatched);
        updateAnimeViewModel.getResponse().observe(getViewLifecycleOwner(), success -> {
            binding.progressBar2.setVisibility(View.GONE);
            if(success){
                Toast.makeText(context,"Anime Updated Successfully!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void setupToolbar(){
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()== R.id.sort){
                launchSortDialog();
            }else{
                Navigation.findNavController(binding.toolbar).navigate(AnimeListFragmentDirections.actionAnimeListFragmentToSearchListFragment());
            }
            return true;
        });
    }

    private void launchSortDialog() {
        if(sortDialog==null){
            sortDialog= new Dialog(getContext());
            sortDialog.setContentView(R.layout.list_sort_dialog);
            sortDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.dialog_background));
            sortDialog.setCancelable(true);
            Button okButton=sortDialog.findViewById(R.id.ok);
            RadioGroup radioGroup=sortDialog.findViewById(R.id.radioGroup);
            Button cancelButton=sortDialog.findViewById(R.id.cancel);
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
                binding.viewPager.setAdapter(new ViewPagerAdapter(context,tabs,viewModel,getViewLifecycleOwner(),AnimeListFragment.this,sortBy));
                sortDialog.dismiss();
            });

            cancelButton.setOnClickListener(v -> sortDialog.dismiss());
        }
        sortDialog.show();
    }


}