package com.omnicoder.anichan.ui.fragments;

import static com.omnicoder.anichan.utils.DAYS.FRIDAY;
import static com.omnicoder.anichan.utils.DAYS.MONDAY;
import static com.omnicoder.anichan.utils.DAYS.SATURDAY;
import static com.omnicoder.anichan.utils.DAYS.SUNDAY;
import static com.omnicoder.anichan.utils.DAYS.THURSDAY;
import static com.omnicoder.anichan.utils.DAYS.TUESDAY;
import static com.omnicoder.anichan.utils.DAYS.WEDNESDAY;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.adapters.recyclerViews.ScheduleAdapter;
import com.omnicoder.anichan.databinding.FragmentScheduleBinding;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.viewModels.ScheduleViewModel;

import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ScheduleFragment extends Fragment {
    private LoadingDialog loadingDialog;
    private FragmentScheduleBinding binding;
    private ScheduleViewModel viewModel;
    private List<JikanSubEntity> sunday;
    private List<JikanSubEntity> monday;
    private List<JikanSubEntity> tuesday;
    private List<JikanSubEntity> wednesday;
    private List<JikanSubEntity> thursday;
    private List<JikanSubEntity> friday;
    private List<JikanSubEntity> saturday;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        loadingDialog=new LoadingDialog(this,getContext());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        initSpinners(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
        observeData();
    }

    private void observeData() {
        androidx.lifecycle.LifecycleOwner lifecycleOwner=getViewLifecycleOwner();
        viewModel.getSunday().observe(lifecycleOwner, jikanSubEntities -> {
            loadingDialog.stopLoading();
            sunday=jikanSubEntities;
            setRecyclerView(sunday);
        });
        viewModel.getMonday().observe(lifecycleOwner, jikanSubEntities -> {
            loadingDialog.stopLoading();
            monday=jikanSubEntities;
            setRecyclerView(monday);
        });
        viewModel.getTuesday().observe(lifecycleOwner, jikanSubEntities -> {
            loadingDialog.stopLoading();
            tuesday=jikanSubEntities;
            setRecyclerView(tuesday);
        });
        viewModel.getWednesday().observe(lifecycleOwner, jikanSubEntities -> {
            loadingDialog.stopLoading();
            wednesday=jikanSubEntities;
            setRecyclerView(wednesday);
        });
        viewModel.getThursday().observe(lifecycleOwner, jikanSubEntities -> {
            loadingDialog.stopLoading();
            thursday=jikanSubEntities;
            setRecyclerView(thursday);
        });
        viewModel.getFriday().observe(lifecycleOwner, jikanSubEntities -> {
            loadingDialog.stopLoading();
            friday=jikanSubEntities;
            setRecyclerView(friday);
        });
        viewModel.getSaturday().observe(lifecycleOwner, jikanSubEntities -> {
            loadingDialog.stopLoading();
            saturday=jikanSubEntities;
            setRecyclerView(saturday);
        });
    }


    private void initSpinners(int day) {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.weekdays, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.scheduleSpinner.setAdapter(arrayAdapter);
        binding.scheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if(sunday==null){
                            loadingDialog.startLoading();
                            viewModel.fetchSchedule(SUNDAY);
                        }else{
                            setRecyclerView(sunday);
                        }
                        break;
                    case 1:
                        if(monday==null){
                            loadingDialog.startLoading();
                            viewModel.fetchSchedule(MONDAY);
                        }else{
                            setRecyclerView(monday);
                        }
                        break;
                    case 2:
                        if(tuesday==null){
                            loadingDialog.startLoading();
                            viewModel.fetchSchedule(TUESDAY);
                        }else{
                            setRecyclerView(tuesday);
                        }
                        break;
                    case 3:
                        if(wednesday==null){
                            loadingDialog.startLoading();
                            viewModel.fetchSchedule(WEDNESDAY);
                        }else{
                            setRecyclerView(wednesday);
                        }
                        break;
                    case 4:
                        if(thursday==null){
                            loadingDialog.startLoading();
                            viewModel.fetchSchedule(THURSDAY);
                        }else{
                            setRecyclerView(thursday);
                        }
                        break;
                    case 5:
                        if(friday==null){
                            loadingDialog.startLoading();
                            viewModel.fetchSchedule(FRIDAY);
                        }else{
                            setRecyclerView(friday);
                        }
                        break;
                    case 6:
                        if(saturday==null){
                            loadingDialog.startLoading();
                            viewModel.fetchSchedule(SATURDAY);
                        }else{
                            setRecyclerView(saturday);
                        }
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.scheduleSpinner.setSelection(day-1);
    }

    private void setRecyclerView(List<JikanSubEntity> weekdaySchedule) {
        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), weekdaySchedule);
        binding.recyclerView.setAdapter(adapter);
    }


    @Override
    public void onPause() {
        super.onPause();
        loadingDialog.stopLoading();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tagg", "onDestroy: Scehdule");
    }
}