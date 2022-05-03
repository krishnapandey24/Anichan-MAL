package com.omnicoder.anichan.UI.Fragments.BottomSheets;

import static com.omnicoder.anichan.Utils.Constants.IMAGE_URL;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.omnicoder.anichan.Database.AnimeList;
import com.omnicoder.anichan.Models.Season;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ViewModels.AnimeListViewModel;
import com.omnicoder.anichan.databinding.AddAnimeBottomSheetBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AddAnimeBottomSheet extends BottomSheetDialogFragment {
    AddAnimeBottomSheetBinding binding;
    String status="Plan To Watch",posterPath,format,mediaType,startDate,finishDate,title,todayDate,airingStatus,listStatus;
    int score=-1,noOfEpisodes=0,totalEpisodes,id,seasonNo,statusPosition;
    AnimeAdded animeAdded;
    boolean addedToList,single,all;
    List<Season> seasons;


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
        initSeasonSelector();
        initSpinner();
        initSeekbar();
        initEpisodeCounter();
        initButtons();
        Log.d("tagg","Airing Status: "+airingStatus+" "+listStatus);
    }

    private void initSeasonSelector() {
        if(single) {
            final String space=" ";
            final String title2=title;
            final String posterPath2=posterPath;
            final int totalEpisodes22=totalEpisodes;
            final String str = "Season ";
            List<String> seasonList = new ArrayList<>();
            seasonList.add("All");
            int size = seasons.size();
            final String specials = "Specials";
            boolean b = seasons.get(0).getName().equals(specials);
            Spinner spinner = binding.chooseSeason.inflate().findViewById(R.id.spinner);
            if (b) {
                for (int i = 1; i < size; i++) {
                    seasonList.add(str + i);
                }
                seasonList.add(specials);
            }else {
                for (int i = 0; i < size; i++) {
                    seasonList.add(str + (i+1));
                }
            }
            int seasonListSize=size+1;
            ArrayAdapter<String> seasonSelectorAdapter = new ArrayAdapter<String>(getContext(), R.layout.drop_down3, seasonList) {
                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view = super.getDropDownView(position, null, parent);
                    TextView textView = view.findViewById(R.id.spinner_text);
                    textView.setTextColor(getResources().getColor(R.color.textColor));
                    return view;
                }
            };
            spinner.setAdapter(seasonSelectorAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    binding.editText.setText("0");
                    binding.spinner.setSelection(1);
                    if (position == 0) {
                        all = true;
                        title=title2;
                        totalEpisodes=totalEpisodes22;
                        posterPath=posterPath2;
                    } else if (b && position == seasonListSize - 1) {
                        Season season= seasons.get(0);
                        title=title2 + space + specials;
                        seasonNo = 0;
                        all = false;
                        posterPath=season.getPoster_path();
                        totalEpisodes = season.getEpisode_count2();
                    } else {
                        Season season = seasons.get(position);
                        title = title2 + space + seasonList.get(position);
                        seasonNo = position;
                        posterPath = IMAGE_URL + season.getPoster_path();
                        totalEpisodes = season.getEpisode_count2();
                        all = false;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            spinner.setSelection(seasonNo);
        }




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
        binding.spinner.setSelection(1);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status= statuses[position];
                statusPosition=position;
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
        FragmentManager fragmentManager=getChildFragmentManager();
        final MaterialDatePicker<Long> materialDatePicker =MaterialDatePicker.Builder.datePicker().build();
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            startDate=materialDatePicker.getHeaderText();
            binding.pickDate.setText(startDate);
        });
        final MaterialDatePicker<Long> materialDatePicker2 =MaterialDatePicker.Builder.datePicker().build();
        materialDatePicker2.addOnPositiveButtonClickListener(selection -> {
            finishDate=materialDatePicker2.getHeaderText();
            binding.pickFinishDate.setText(finishDate);
        });
        binding.pickDate.setOnClickListener(v -> materialDatePicker.show(fragmentManager,"DatePicker"));
        binding.pickFinishDate.setOnClickListener(v -> materialDatePicker2.show(fragmentManager,"DatePicker2"));

        todayDate= new SimpleDateFormat("d MMM yyyy", Locale.US).format(new Date());
        binding.startDateToday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                binding.pickDate.setText(todayDate);
            }else {
                binding.pickDate.setText("Pick Start Date");
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
        if (addedToList){
            binding.addToListButton.setText("Update");
        }
        binding.addToListButton.setOnClickListener(v -> {
            String airingStatus2= airingStatus.equals("Next Season In Production") ? (all ? airingStatus : listStatus ) : airingStatus;
            AnimeListViewModel animeListViewModel = new ViewModelProvider(this).get(AnimeListViewModel.class);
            AnimeList animeList = new AnimeList(id, score, totalEpisodes, seasonNo, Integer.parseInt(binding.editText.getText().toString()), status, title, posterPath, startDate, finishDate, format, mediaType, statusPosition,airingStatus2);
            animeListViewModel.addAnime(animeList);
            animeAdded.setStatus(status);
            dismiss();
        });

        binding.cancelButton.setOnClickListener(v -> dismiss());
    }

    public interface AnimeAdded{
        void setStatus(String status);
    }

    public void setData(String title,int totalEpisodes,int id,int seasonNo,String posterPath,String format,String mediaType,boolean addedToList,boolean single,String status,String listStatus) {
        this.title = title;
        this.totalEpisodes=totalEpisodes;
        this.id=id;
        this.seasonNo=seasonNo;
        this.posterPath=posterPath;
        this.format=format;
        this.mediaType=mediaType;
        this.addedToList=addedToList;
        this.single=single;
        this.airingStatus=status;
        this.listStatus=listStatus;
    }

    public void setSeasons(List<Season> seasons){
        this.seasons=seasons;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        animeAdded=(AnimeAdded) context;
    }


}
