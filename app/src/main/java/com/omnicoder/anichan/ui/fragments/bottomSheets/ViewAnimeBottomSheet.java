package com.omnicoder.anichan.UI.Fragments.BottomSheets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.omnicoder.anichan.databinding.ViewBottomSheetBinding;

import org.jetbrains.annotations.NotNull;

public class ViewAnimeBottomSheet extends BottomSheetDialogFragment {
    ViewBottomSheetBinding binding;
    MenuBottomSheet menuBottomSheet;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding= ViewBottomSheetBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.applyButton.setOnClickListener(v -> {
            String sortBy= ((RadioButton)binding.sortBy.findViewById(binding.sortBy.getCheckedRadioButtonId())).getText().toString();
            String airingStatus=((RadioButton)binding.airingStatus.findViewById(binding.airingStatus.getCheckedRadioButtonId())).getText().toString();
            String format=((RadioButton)binding.type.findViewById(binding.type.getCheckedRadioButtonId())).getText().toString();
            menuBottomSheet.sortAnimeList(sortBy,airingStatus,format);
            dismiss();
        });

        binding.cancelButton.setOnClickListener(v -> dismiss());
    }

    public interface MenuBottomSheet{
        void sortAnimeList(String sortBy,String airingStatus,String format);

    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        menuBottomSheet=(MenuBottomSheet) context;
    }
}
