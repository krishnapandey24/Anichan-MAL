package com.omnicoder.anichan.ui.fragments;

import android.os.Bundle;
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

import com.omnicoder.anichan.adapters.ScheduleAdapter;
import com.omnicoder.anichan.models.jikan.Schedule;
import com.omnicoder.anichan.models.jikan.ScheduleAnimeEntity;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.viewModels.ScheduleViewModel;
import com.omnicoder.anichan.databinding.FragmentScheduleBinding;

import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ScheduleFragment extends Fragment {

    private FragmentScheduleBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ScheduleViewModel viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        viewModel.fetchSchedule();
        viewModel.getSchedule().observe(getViewLifecycleOwner(), schedule -> initSpinners(schedule,Calendar.getInstance().get(Calendar.DAY_OF_WEEK)));
    }


    private void initSpinners(Schedule schedule,int day) {
        binding.progressBar.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.weekdays, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.scheduleSpinner.setAdapter(arrayAdapter);
        binding.scheduleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        setRecyclerView(schedule.getSunday());
                        break;
                    case 1:
                        setRecyclerView(schedule.getMonday());
                        break;
                    case 2:
                        setRecyclerView(schedule.getTuesday());
                        break;
                    case 3:
                        setRecyclerView(schedule.getWednesday());
                        break;
                    case 4:
                        setRecyclerView(schedule.getThursday());
                        break;
                    case 5:
                        setRecyclerView(schedule.getFriday());
                        break;
                    case 6:
                        setRecyclerView(schedule.getSaturday());
                        break;
                    case 7:
                        setRecyclerView(schedule.getOther());
                        break;
                    case 8:
                        setRecyclerView(schedule.getUnknown());
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.scheduleSpinner.setSelection(day-1);
    }

    private void setRecyclerView(List<ScheduleAnimeEntity> weekdaySchedule) {
        ScheduleAdapter adapter = new ScheduleAdapter(getContext(), weekdaySchedule);
        binding.recyclerView.setAdapter(adapter);
    }
}