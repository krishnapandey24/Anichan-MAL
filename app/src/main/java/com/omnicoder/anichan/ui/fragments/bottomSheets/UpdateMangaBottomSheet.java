package com.omnicoder.anichan.ui.fragments.bottomSheets;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.omnicoder.anichan.database.UserManga;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.MangaBottomSheetBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class UpdateMangaBottomSheet extends BottomSheetDialogFragment {
    MangaBottomSheetBinding binding;
    UserManga manga;
    UpdateManga updateMangaInterface;
    int position, score = -1, noOfVolumes, noOfChapters, totalVolumes, totalChapters;
    String todayDate;
    int spinnerCounter=0; // counter to avoid OnItemSelectedListener while initializing the spinner


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MangaBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatePicker();
        initSpinner();
        initSeekbar();
        initButtons();
        initCounters();

    }



    private void initSpinner() {
        String[] statuses = getResources().getStringArray(R.array.MangaStatuses);
        String[] malStatus = getResources().getStringArray(R.array.malMangaStatuses);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(getContext(), R.layout.drop_down3, statuses) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, null, parent);
                TextView textView = view.findViewById(R.id.spinner_text);
                textView.setTextColor(getResources().getColor(R.color.textColor));
                return view;
            }
        };
        binding.spinner.setAdapter(statusAdapter);
        binding.spinner.setSelection(position);
        binding.spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                manga.setStatus(malStatus[position]);
                if(++spinnerCounter>1){
                    switch (position) {
                        case 0:
                            binding.pickStartDate.setText(todayDate);
                            break;
                        case 1:
                            binding.editVolumes.setText("0");
                            binding.editChapters.setText("0");
                            break;
                        case 2:
                            binding.editVolumes.setText(String.valueOf(totalVolumes));
                            binding.editChapters.setText(String.valueOf(totalChapters));
                            break;
                        case 5:
                            manga.setStatus(malStatus[0]);
                            manga.setIs_rereading(true);
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSeekbar() {
        int red = Color.RED;
        int yellow = Color.parseColor("#F6BE00");
        int green = Color.parseColor("#00B16A");
        int blue = Color.parseColor("#2F80ED");
        int black = Color.parseColor("#000000");
        int progress = manga.getScore();
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                score = progress;
                manga.setScore(score);
                switch (progress) {
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
        if (progress != -1) {
            binding.seekBar.setProgress(progress);
        }

    }

    private void initCounters() {
        Context context = getContext();
        String title = manga.getTitle();
        binding.editVolumes.setText(String.valueOf(manga.getNoOfVolumesRead()));
        binding.editChapters.setText(String.valueOf(manga.getNoOfChaptersRead()));
        binding.addButton.setOnClickListener(v -> {
            noOfVolumes = Integer.parseInt(binding.editVolumes.getText().toString());
            if (noOfVolumes == totalVolumes && totalVolumes!=0) {
                Toast.makeText(context, title + " Only have " + totalVolumes + " volumes.", Toast.LENGTH_SHORT).show();
            } else {
                noOfVolumes++;
                binding.editVolumes.setText(String.valueOf(noOfVolumes));
            }
        });

        binding.minusButton.setOnClickListener(v -> {
            noOfVolumes = Integer.parseInt(binding.editVolumes.getText().toString());
            noOfVolumes = noOfVolumes != 0 ? noOfVolumes - 1 : 0;
            binding.editVolumes.setText(String.valueOf(noOfVolumes));
        });

        binding.addChapters.setOnClickListener(v -> {
            noOfChapters = Integer.parseInt(binding.editChapters.getText().toString());
            if (noOfChapters == totalChapters && totalChapters!=0) {
                Toast.makeText(context, title + " Only have " + totalChapters + " chapters.", Toast.LENGTH_SHORT).show();
            } else {
                noOfChapters++;
                binding.editChapters.setText(String.valueOf(noOfChapters));
            }
        });

        binding.minusChapter.setOnClickListener(v -> {
            noOfChapters = Integer.parseInt(binding.editChapters.getText().toString());
            noOfChapters = noOfChapters != 0 ? noOfChapters - 1 : 0;
            binding.editChapters.setText(String.valueOf(noOfChapters));
        });
    }

    @SuppressLint("SetTextI18n")
    private void initDatePicker() {
        String mangaStartDate = manga.getStartDate();
        String mangaFinishDate = manga.getFinishData();
        if (mangaStartDate != null) {
            binding.pickStartDate.setText(mangaStartDate);
        }

        if (mangaFinishDate != null) {
            binding.pickFinishDate.setText(mangaFinishDate);
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        final MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().build();
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            String startDate = materialDatePicker.getHeaderText();
            manga.setStartDate(startDate);
            binding.pickStartDate.setText(startDate);
        });
        final MaterialDatePicker<Long> materialDatePicker2 = MaterialDatePicker.Builder.datePicker().build();
        materialDatePicker2.addOnPositiveButtonClickListener(selection -> {
            String finishDate = materialDatePicker2.getHeaderText();
            manga.setFinishData(finishDate);
            binding.pickFinishDate.setText(finishDate);
        });
        binding.pickStartDate.setOnClickListener(v -> materialDatePicker.show(fragmentManager, "DatePicker"));
        binding.pickFinishDate.setOnClickListener(v -> materialDatePicker2.show(fragmentManager, "DatePicker2"));

        todayDate = new SimpleDateFormat("d MMM yyyy", Locale.US).format(new Date());
        binding.startDateToday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.pickStartDate.setText(todayDate);
                manga.setStartDate(todayDate);
            } else {
                binding.pickStartDate.setText("Pick Start Date");
            }
        });

        binding.finishDateToday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.pickFinishDate.setText(todayDate);
                manga.setFinishData(todayDate);
            } else {
                binding.pickFinishDate.setText("Pick Finish Date");
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initButtons() {
        binding.addToListButton.setText("Update");
        binding.cancelButton.setText("Remove");

        binding.addToListButton.setOnClickListener(v -> {
            manga.setNoOfVolumesRead(Integer.parseInt(binding.editVolumes.getText().toString()));
            manga.setNoOfChaptersRead(Integer.parseInt(binding.editChapters.getText().toString()));
            updateMangaInterface.updateManga(manga, position);
            dismiss();
        });

        binding.cancelButton.setOnClickListener(v -> {
            AlertDialog.Builder alterDialog = new AlertDialog.Builder(requireContext());
            alterDialog.setTitle("Remove manga from the list");
            alterDialog.setMessage("Are you sure you want to remove this from from your list?");
            alterDialog.setPositiveButton("YES", (dialog, which) -> {
                updateMangaInterface.deleteManga(manga.getId());
                dismiss();
            });
            alterDialog.setNegativeButton("NO", (dialog, which) -> dialog.cancel());
            alterDialog.show();
        });


    }

    public interface UpdateManga {
        void updateManga(UserManga manga, int position);
        void deleteManga(int id);
    }


    public void setManga(UserManga manga, UpdateMangaBottomSheet.UpdateManga updateMangaInterface, int position) {
        this.manga = manga;
        this.updateMangaInterface = updateMangaInterface;
        totalVolumes = manga.getNoOfVolumes();
        totalChapters = manga.getNoOfChapters();
        this.position = position;
    }


}
