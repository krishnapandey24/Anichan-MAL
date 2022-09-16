package com.omnicoder.anichan.UI.Fragments;

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
import com.omnicoder.anichan.Adapters.MangaViewPagerAdapter;
import com.omnicoder.anichan.Database.UserManga;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Fragments.BottomSheets.UpdateMangaBottomSheet;
import com.omnicoder.anichan.ViewModels.MangaListViewModel;
import com.omnicoder.anichan.ViewModels.UpdateMangaViewModel;
import com.omnicoder.anichan.databinding.FragmentMangaListBinding;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class MangaListFragment extends Fragment implements MangaViewPagerAdapter.MangaPagerAdapterInterface {
    private FragmentMangaListBinding binding;
    private MangaListViewModel mangaListViewModel;
    private UpdateMangaViewModel updateMangaViewModel;
    private Dialog sortDialog=null;
    private String[] tabs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMangaListBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mangaListViewModel = new ViewModelProvider(this).get(MangaListViewModel.class);
        mangaListViewModel.fetchUserMangaList();
        mangaListViewModel.getMangaListFetchedStatus().observe(getViewLifecycleOwner(), aBoolean -> binding.progressBar.setVisibility(View.GONE));
        updateMangaViewModel= new ViewModelProvider(this).get(UpdateMangaViewModel.class);
        tabs = getResources().getStringArray(R.array.MangaStatuses);
        setTabLayout();
        setupToolbar();
    }
    private void setTabLayout(){
        binding.viewPager.setAdapter(new MangaViewPagerAdapter(getContext(),tabs, mangaListViewModel,getViewLifecycleOwner(),MangaListFragment.this,"t_id"));
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(tabs[position])).attach();
    }

    @Override
    public void updateManga(UserManga userManga, int position) {
        binding.progressBar.setVisibility(View.VISIBLE);
        observeAndShowToast(updateMangaViewModel.getUpdateMangaResponse());
        updateMangaViewModel.updateManga(userManga.getId(),userManga.getStatus(),userManga.isIs_rereading(),userManga.getScore(),userManga.getNoOfVolumesRead(),userManga.getNoOfChaptersRead());
        updateMangaViewModel.insertOrUpdateMangaInList(userManga);

    }

    @Override
    public void addChapter(int id,int noOfChaptersRead) {
        binding.progressBar.setVisibility(View.VISIBLE);
        observeAndShowToast(updateMangaViewModel.getResponse());
        updateMangaViewModel.addChapter(id,noOfChaptersRead);
    }

    @Override
    public void showEditor(UpdateMangaBottomSheet updateMangaBottomSheet) {
        updateMangaBottomSheet.show(getChildFragmentManager(), "UpdateMangaSheet");
    }


    @Override
    public void mangaCompleted(int id,String name) {
        updateMangaViewModel.mangaCompleted(id);
        Toast.makeText(getContext(),name+" Completed!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteManga(int id) {
        binding.progressBar.setVisibility(View.VISIBLE);
        observeAndShowToast(updateMangaViewModel.deleteResponse());
        updateMangaViewModel.deleteManga(id);
    }

    private void setupToolbar(){
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId()== R.id.sort){
                launchSortDialog();
            }else{
                Navigation.findNavController(binding.toolbar).navigate(MangaListFragmentDirections.actionMangaListFragmentToSearchListFragment(false));
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
                binding.viewPager.setAdapter(new MangaViewPagerAdapter(getContext(),tabs, mangaListViewModel,getViewLifecycleOwner(),MangaListFragment.this,sortBy));
                sortDialog.dismiss();
            });
            cancelButton.setOnClickListener(v -> sortDialog.dismiss());
            sortDialog.show();
            sortDialog.getWindow().setAttributes(layoutParams);
        }

    }

    private void observeAndShowToast(MutableLiveData<Boolean> response) {
        response.observe(getViewLifecycleOwner(), success -> {
            binding.progressBar.setVisibility(View.GONE);
            if (success) {
                Toast.makeText(getContext(), "Manga List Updated Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Something went wrong! \n Please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


}