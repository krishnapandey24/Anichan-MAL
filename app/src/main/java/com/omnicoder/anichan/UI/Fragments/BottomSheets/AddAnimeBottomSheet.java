package com.omnicoder.anichan.UI.Fragments.BottomSheets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.omnicoder.anichan.Database.UserAnime;
import com.omnicoder.anichan.Models.AnimeResponse.Anime;
import com.omnicoder.anichan.Models.AnimeResponse.AnimeListStatus;
import com.omnicoder.anichan.Models.AnimeResponse.StartSeason;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ViewModels.UpdateAnimeViewModel;
import com.omnicoder.anichan.databinding.AddAnimeBottomSheetBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AddAnimeBottomSheet extends BottomSheetDialogFragment {
    AddAnimeBottomSheetBinding binding;
    Anime anime;
    String status="Plan To Watch",startDate,finishDate,todayDate,selectedStatus;
    int score=0,noOfEpisodes=0,totalEpisodes,statusPosition;
    AnimeAdded animeAdded;
    AnimeListStatus animeListStatus;
    boolean rewatching=false;


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
        AlreadyAdded();
    }

    private void AlreadyAdded() {
        if(animeListStatus!=null){
            status=animeListStatus.getStatus();
            switch (status){
                case "watching":
                    statusPosition=0;
                    rewatching=false;
                    break;
                case "plan_to_watch":
                    statusPosition=1;
                    rewatching=false;
                    break;
                case "completed":
                    statusPosition=2;
                    rewatching=false;
                    break;
                case "on_hold":
                    statusPosition=3;
                    rewatching=false;
                    break;
                case "dropped":
                    statusPosition=4;
                    break;
            }
            binding.spinner.setSelection(statusPosition);
            binding.editText.setText(String.valueOf(animeListStatus.getNum_episodes_watched()));
            binding.seekBar.setProgress(animeListStatus.getScore());
            startDate=animeListStatus.getStart_date();
            if(startDate!=null){
                binding.pickStartDate.setText(animeListStatus.getStart_date());
            }
            finishDate=animeListStatus.getFinish_date();
            if(finishDate!=null){
                binding.pickFinishDate.setText(animeListStatus.getFinish_date());
            }
            noOfEpisodes= animeListStatus.getNum_episodes_watched();
            startDate=animeListStatus.getStart_date();
            finishDate=animeListStatus.getFinish_date();
            score=animeListStatus.getScore();
            binding.editText.setText(String.valueOf(noOfEpisodes));
        }
    }






    private void initSpinner() {
        String[] statuses =getResources().getStringArray(R.array.Statuses2);
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
        binding.spinner.setSelection(1);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status= statuses[position];
                statusPosition=position;
                switch (position){
                    case 0:
                        binding.pickStartDate.setText(todayDate);
                        selectedStatus="watching";
                        break;
                    case 1:
                        binding.editText.setText("0");
                        selectedStatus="plan_to_watch";
                        break;
                    case 2:
                        binding.editText.setText(String.valueOf(totalEpisodes));
                        selectedStatus="completed";
                        break;
                    case 3:
                        selectedStatus="on_hold";
                        break;
                    case 4:
                        selectedStatus="dropped";
                    case 5:
                        selectedStatus="watching";
                        rewatching=true;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSeekbar(){
        int red=Color.RED;
        int yellow = Color.parseColor("#F6BE00");
        int green= Color.parseColor("#00B16A");
        int blue= Color.parseColor("#2F80ED");
        int black= Color.parseColor("#000000");

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                score=progress;
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
    }

    private void initEpisodeCounter(){
        Context context=getContext();
        binding.addButton2.setOnClickListener(v -> {
            noOfEpisodes=Integer.parseInt(binding.editText.getText().toString());
            if(totalEpisodes==0){
                Toast.makeText(context,"Episodes haven't released yet",Toast.LENGTH_SHORT).show();
            }
            else if(noOfEpisodes==totalEpisodes){
                Toast.makeText(context,anime.getTitle()+" Only have "+totalEpisodes+" Episodes.",Toast.LENGTH_SHORT).show();
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
        FragmentManager fragmentManager=getChildFragmentManager();
        final MaterialDatePicker<Long> materialDatePicker =MaterialDatePicker.Builder.datePicker().build();
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            startDate=materialDatePicker.getHeaderText();
            binding.pickStartDate.setText(startDate);
        });
        final MaterialDatePicker<Long> materialDatePicker2 =MaterialDatePicker.Builder.datePicker().build();
        materialDatePicker2.addOnPositiveButtonClickListener(selection -> {
            finishDate=materialDatePicker2.getHeaderText();
            binding.pickFinishDate.setText(finishDate);
        });
        binding.pickStartDate.setOnClickListener(v -> materialDatePicker.show(fragmentManager,"DatePicker"));
        binding.pickFinishDate.setOnClickListener(v -> materialDatePicker2.show(fragmentManager,"DatePicker2"));

        todayDate= new SimpleDateFormat("d MMM yyyy", Locale.US).format(new Date());
        binding.startDateToday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.pickStartDate.setText(todayDate);
            }else {
                binding.pickStartDate.setText("Pick Start Date");
            }
        });

        binding.finishDateToday.setOnCheckedChangeListener((buttonView,isChecked)->{
            if(isChecked){
                binding.pickFinishDate.setText(todayDate);
            }else {
                binding.pickFinishDate.setText("Pick Finish Date");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initButtons(){
        binding.addToListButton.setOnClickListener(v -> {
            animeAdded.startLoading();
            UpdateAnimeViewModel updateAnimeViewModel = new ViewModelProvider(this).get(UpdateAnimeViewModel.class);
            updateAnimeViewModel.updateAnime(anime.getId(),selectedStatus,rewatching,score,Integer.valueOf(binding.editText.getText().toString()));
            animeAdded.setResponseToObserve(updateAnimeViewModel.getResponse());
            String mainPicture=anime.getMainPicture()==null ? "" : anime.getMainPicture().getMedium();
            StartSeason startSeason= anime.getStart_season();
            String season;
            if(startSeason==null){
                season="";
            }else{
                season=startSeason.getSeason() + " " + startSeason.getYear();
            }
            UserAnime userAnime=new UserAnime(anime.getId(),anime.getTitle(),mainPicture,anime.getMedia_type(),season,selectedStatus,startDate,finishDate,score,Integer.parseInt(binding.editText.getText().toString()), anime.getNum_episodes(),rewatching);
            updateAnimeViewModel.insertOrUpdateAnimeInList(userAnime);
            animeAdded.setStatus(status);
            dismiss();
        });

        binding.cancelButton.setOnClickListener(v -> dismiss());
    }

    public interface AnimeAdded{
        void setStatus(String status);
        void startLoading();
        void setResponseToObserve(MutableLiveData<Boolean> response);
    }



    public void setData(Anime anime) {
        this.anime=anime;
        this.totalEpisodes=anime.getNum_episodes();
        this.animeListStatus=anime.getMy_list_status();
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        animeAdded=(AnimeAdded) context;
    }


}
