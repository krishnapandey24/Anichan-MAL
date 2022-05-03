package com.omnicoder.anichan.UI.Fragments.BottomSheets;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.omnicoder.anichan.Database.AnimeList;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.AddAnimeBottomSheetBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class UpdateAnimeBottomSheet extends BottomSheetDialogFragment {
    AddAnimeBottomSheetBinding binding;
    AnimeList animeList;
    UpdateAnime updateAnimeInterface;
    int position,score=-1,noOfEpisodes,totalEpisodes;
    String todayDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=AddAnimeBottomSheetBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatePicker();
        initSpinner();
        initSeekbar();
        initEpisodeCounter();
        initButtons();


    }

    private void initSpinner() {
        String[] statuses=getResources().getStringArray(R.array.Statuses2);
        ArrayAdapter<String> statusAdapter= new ArrayAdapter<String>(getContext(),R.layout.drop_down3,statuses){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, null, parent);
                TextView textView= view.findViewById(R.id.spinner_text);
                textView.setTextColor(getResources().getColor(R.color.textColor));
                return view;
            }
        };
        binding.spinner.setAdapter(statusAdapter);
        binding.spinner.setSelection(position);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                animeList.setStatus(statuses[position],position);
                switch (position){
                    case 0:
                        binding.pickDate.setText(todayDate);
                        break;
                    case 1:
                        binding.editText.setText("0");
                        break;
                    case 2:
                        binding.editText.setText(String.valueOf(totalEpisodes));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSeekbar(){
        int red=Color.parseColor("#DA5E52");
        int yellow = Color.parseColor("#FFB74E");
        int green= Color.parseColor("#87DE75");
        int blue= Color.parseColor("#2F80ED");
        int black= Color.parseColor("#FF000000");
        int progress=animeList.getGivenScore();
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                score=progress;
                animeList.setGivenScore(score);
                switch (progress){
                    case 0:
                        binding.givenScoreView.setText("--");
                        binding.givenScoreView.setTextColor(black);
                        break;
                    case 1:
                        binding.givenScoreView.setText("1 (Appalling)");
                        binding.givenScoreView.setTextColor(red);
                        break;
                    case 2:
                        binding.givenScoreView.setText("2 (Horrible)");
                        binding.givenScoreView.setTextColor(red);
                        break;
                    case 3:
                        binding.givenScoreView.setText("3 (Very Bad)");
                        binding.givenScoreView.setTextColor(red);
                        break;
                    case 4:
                        binding.givenScoreView.setText("4 (Bad)");
                        binding.givenScoreView.setTextColor(red);
                        break;
                    case 5:
                        binding.givenScoreView.setText("5 (Average)");
                        binding.givenScoreView.setTextColor(yellow);
                        break;
                    case 6:
                        binding.givenScoreView.setText("6 (Fine)");
                        binding.givenScoreView.setTextColor(yellow);
                        break;
                    case 7:
                        binding.givenScoreView.setText("7 (Good)");
                        binding.givenScoreView.setTextColor(yellow);
                        break;
                    case 8:
                        binding.givenScoreView.setText("8 (Very Good)");
                        binding.givenScoreView.setTextColor(green);
                        break;
                    case 9:
                        binding.givenScoreView.setText("9 (Great)");
                        binding.givenScoreView.setTextColor(green);
                        break;
                    case 10:
                        binding.givenScoreView.setText("10 (Masterpiece)");
                        binding.givenScoreView.setTextColor(blue);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if(progress!=-1){
            binding.seekBar.setProgress(progress);
        }
    }

    private void initEpisodeCounter(){
        Context context=getContext();
        String title=animeList.getTitle();
        binding.editText.setText(String.valueOf(animeList.getWatchedEpisodes()));
        binding.addButton2.setOnClickListener(v -> {
            noOfEpisodes=Integer.parseInt(binding.editText.getText().toString());
            if(noOfEpisodes==totalEpisodes){
                Toast.makeText(context,title+" Only have "+totalEpisodes+" Episodes.",Toast.LENGTH_SHORT).show();
            }else {
                noOfEpisodes++;
                binding.editText.setText(String.valueOf(noOfEpisodes));
            }

        });

        binding.minusButton2.setOnClickListener(v -> {
            noOfEpisodes=Integer.parseInt(binding.editText.getText().toString());
            noOfEpisodes= noOfEpisodes!=0 ? noOfEpisodes-1 : 0;
            binding.editText.setText(String.valueOf(noOfEpisodes));
        });
    }

    @SuppressLint("SetTextI18n")
    private void initDatePicker(){
        String animeStartDate=animeList.getStartedDate();
        String animeFinishDate=animeList.getCompletedDate();
        if(animeStartDate!=null){
            binding.pickDate.setText(animeList.getStartedDate());
        }
        if(animeFinishDate!=null){
            binding.pickFinishDate.setText(animeList.getCompletedDate());
        }
        FragmentManager fragmentManager=getChildFragmentManager();
        final MaterialDatePicker<Long> materialDatePicker =MaterialDatePicker.Builder.datePicker().build();
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            String startDate=materialDatePicker.getHeaderText();
            animeList.setStartedDate(startDate);
            binding.pickDate.setText(startDate);
        });
        final MaterialDatePicker<Long> materialDatePicker2 =MaterialDatePicker.Builder.datePicker().build();
        materialDatePicker2.addOnPositiveButtonClickListener(selection -> {
            String finishDate= materialDatePicker2.getHeaderText();
            animeList.setStartedDate(finishDate);
            binding.pickFinishDate.setText(finishDate);
        });
        binding.pickDate.setOnClickListener(v -> materialDatePicker.show(fragmentManager,"DatePicker"));
        binding.pickFinishDate.setOnClickListener(v -> materialDatePicker2.show(fragmentManager,"DatePicker2"));

        todayDate= new SimpleDateFormat("d MMM yyyy", Locale.US).format(new Date());
        binding.startDateToday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.pickDate.setText(todayDate);
                animeList.setStartedDate(todayDate);
            }else {
                binding.pickDate.setText("Pick Start Date");
            }
        });

        binding.finishDateToday.setOnCheckedChangeListener((buttonView,isChecked)->{
            if(isChecked){
                binding.pickFinishDate.setText(todayDate);
                animeList.setCompletedDate(todayDate);
            }else {
                binding.pickFinishDate.setText("Pick Finish Date");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initButtons(){
        binding.addToListButton.setText("Update");
        binding.addToListButton.setOnClickListener(v -> {
            updateAnimeInterface.updateAnime(animeList,position);
            dismiss();
        });
        binding.cancelButton.setText("Remove");
        binding.cancelButton.setOnClickListener(v -> {
            AlertDialog.Builder alterDialog = new AlertDialog.Builder(getContext());
            alterDialog.setTitle("Remove anime from the list");
            alterDialog.setMessage("Are you sure you want to remove this from from your list?");
            alterDialog.setPositiveButton("YES", (dialog, which) -> {
                updateAnimeInterface.deleteAnime(animeList.getAnimeID());
                dismiss();
            });
            alterDialog.setNegativeButton("NO",(dialog,which)->{
                dialog.cancel();
            });
            alterDialog.show();
        });


    }



    public void setAnime(AnimeList animeList,UpdateAnimeBottomSheet.UpdateAnime updateAnimeInterface){
        this.animeList=animeList;
        this.updateAnimeInterface=updateAnimeInterface;
        totalEpisodes=animeList.getTotalEpisode();
        position=animeList.getStatusPosition();

    }

    public interface UpdateAnime{
        void updateAnime(AnimeList animeList,int position);
        void deleteAnime(int id);
    }

}
