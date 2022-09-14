package com.omnicoder.anichan.UI.Fragments.BottomSheets;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.omnicoder.anichan.Database.UserManga;
import com.omnicoder.anichan.Models.MangaResponse.Manga;
import com.omnicoder.anichan.Models.MangaResponse.MangaListStatus;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ViewModels.UpdateMangaViewModel;
import com.omnicoder.anichan.databinding.MangaBottomSheetBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class AddMangaBottomSheet extends BottomSheetDialogFragment {
    MangaBottomSheetBinding binding;
    Manga manga;
    String status="Plan To Read",startDate,finishDate,todayDate,selectedStatus;
    int score=0,noOfVolumes=0,noOfChapters,totalVolumes,totalChapters,statusPosition;
    MangaAdded mangaAdded;
    MangaListStatus mangaListStatus;
    boolean rereading =false;
    private static final String update="Update";
    private static final String remove="Remove";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=MangaBottomSheetBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatePicker();
        initSpinner();
        initSeekbar();
        initCounters();
        initButtons();
        AlreadyAdded();
    }

    private void AlreadyAdded() {
        if(mangaListStatus!=null){
            status=mangaListStatus.getStatus();
            switch (status){
                case "watching":
                    statusPosition=0;
                    rereading =false;
                    break;
                case "plan_to_read":
                    statusPosition=1;
                    rereading =false;
                    break;
                case "completed":
                    statusPosition=2;
                    rereading =false;
                    break;
                case "on_hold":
                    statusPosition=3;
                    rereading =false;
                    break;
                case "dropped":
                    statusPosition=4;
                    break;
            }
            binding.spinner.setSelection(statusPosition);
            binding.editVolumes.setText(String.valueOf(mangaListStatus.getNum_volumes_read()));
            binding.editChapters.setText(String.valueOf(manga.getNum_chapters()));
            binding.seekBar.setProgress(mangaListStatus.getScore());
            startDate=mangaListStatus.getStart_date();
            if(startDate!=null){
                binding.pickStartDate.setText(mangaListStatus.getStart_date());
            }
            finishDate=mangaListStatus.getFinish_date();
            if(finishDate!=null){
                binding.pickFinishDate.setText(mangaListStatus.getFinish_date());
            }
            noOfVolumes= mangaListStatus.getNum_volumes_read();
            noOfChapters= mangaListStatus.getNum_chapters_read();
            startDate=mangaListStatus.getStart_date();
            finishDate=mangaListStatus.getFinish_date();
            score=mangaListStatus.getScore();
            binding.editVolumes.setText(String.valueOf(noOfVolumes));
            binding.editChapters.setText(String.valueOf(noOfChapters));
            binding.addToListButton.setText(update);
            binding.cancelButton.setText(remove);
        }
    }






    private void initSpinner() {
        String[] statuses =getResources().getStringArray(R.array.MangaStatuses);
        String[] malStatus=getResources().getStringArray(R.array.malMangaStatuses);
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
                        selectedStatus=malStatus[0];
                        break;
                    case 1:
                        binding.editVolumes.setText("0");
                        binding.editChapters.setText("0");
                        selectedStatus=malStatus[1];
                        break;
                    case 2:
                        binding.editVolumes.setText(String.valueOf(totalVolumes));
                        binding.editChapters.setText(String.valueOf(totalChapters));
                        selectedStatus=malStatus[2];
                        break;
                    case 3:
                        selectedStatus=malStatus[3];
                        break;
                    case 4:
                        selectedStatus=malStatus[4];
                        break;
                    case 5:
                        selectedStatus=malStatus[1];
                        rereading =true;
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

    private void initCounters(){
        Context context=getContext();
        binding.addButton.setOnClickListener(v -> {
            noOfVolumes=Integer.parseInt(binding.editVolumes.getText().toString());
            if(noOfVolumes==totalVolumes && totalVolumes!=0){
                Toast.makeText(context,manga.getTitle()+" Only have "+totalVolumes+" volumes.",Toast.LENGTH_SHORT).show();
            }else {
                noOfVolumes++;
                binding.editVolumes.setText(String.valueOf(noOfVolumes));
            }

        });

        binding.minusButton.setOnClickListener(v -> {
            noOfVolumes=Integer.parseInt(binding.editVolumes.getText().toString());
            noOfVolumes= noOfVolumes!=0 ? noOfVolumes-1 : 0;
            binding.editVolumes.setText(String.valueOf(noOfVolumes));
        });

        binding.addChapters.setOnClickListener(v -> {
            noOfChapters=Integer.parseInt(binding.editChapters.getText().toString());
            if(noOfChapters==totalChapters && totalChapters!=0){
                Toast.makeText(context,manga.getTitle()+" Only have "+totalChapters+" chapters.",Toast.LENGTH_SHORT).show();
            }else {
                noOfChapters++;
                binding.editChapters.setText(String.valueOf(noOfChapters));
            }

        });

        binding.minusChapter.setOnClickListener(v -> {
            noOfChapters=Integer.parseInt(binding.editChapters.getText().toString());
            noOfChapters= noOfChapters!=0 ? noOfChapters-1 : 0;
            binding.editChapters.setText(String.valueOf(noOfChapters));
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
        UpdateMangaViewModel viewModel = new ViewModelProvider(this).get(UpdateMangaViewModel.class);
        binding.addToListButton.setOnClickListener(v -> {
            mangaAdded.startLoading();
            int volumes=Integer.parseInt(binding.editVolumes.getText().toString());
            int chapters=Integer.parseInt(binding.editChapters.getText().toString());
            viewModel.updateManga(manga.getId(),selectedStatus, rereading,score,volumes,chapters);
            mangaAdded.setResponseToObserve(viewModel.getUpdateMangaResponse());
            String mainPicture=manga.getMainPicture()==null ? "" : manga.getMainPicture().getMedium();
            UserManga userManga=new UserManga(manga.getId(),manga.getTitle(),mainPicture,selectedStatus,startDate,finishDate,score,volumes,chapters, manga.getNum_volumes(),manga.getNum_chapters(), rereading);
            viewModel.insertOrUpdateMangaInList(userManga);
            mangaAdded.setStatus(status);
            dismiss();
        });

        if(mangaListStatus==null){
            binding.cancelButton.setOnClickListener(v -> dismiss());
        }else{
            mangaAdded.startLoading();
            AlertDialog.Builder alterDialog = new AlertDialog.Builder(getContext());
            alterDialog.setTitle("Remove anime from the list");
            alterDialog.setMessage("Are you sure you want to remove this from from your list?");
            alterDialog.setPositiveButton("YES", (dialog, which) -> {
                viewModel.deleteManga(manga.getId());
                mangaAdded.observeDeleteResponse(viewModel.deleteResponse());
                dismiss();
            });
            alterDialog.setNegativeButton("NO",(dialog,which)-> dialog.cancel());
            alterDialog.show();
        }
    }



    public interface MangaAdded{
        void setStatus(String status);
        void startLoading();
        void setResponseToObserve(MutableLiveData<Boolean> response);
        void observeDeleteResponse(MutableLiveData<Boolean> response);
    }



    public void setData(Manga manga) {
        this.manga=manga;
        this.totalVolumes=manga.getNum_volumes();
        this.totalChapters=manga.getNum_chapters();
        this.mangaListStatus=manga.getMy_list_status();
    }





    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mangaAdded=(MangaAdded) context;
    }


}
