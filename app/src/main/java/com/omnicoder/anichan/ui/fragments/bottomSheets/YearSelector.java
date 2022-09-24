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
import com.omnicoder.anichan.databinding.SeasonSelectorBinding;

import org.jetbrains.annotations.NotNull;

public class YearSelector extends BottomSheetDialogFragment {
    SeasonSelectorBinding binding;
    YearSheet YearSheet;
    public int index=0;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding= SeasonSelectorBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((RadioButton)binding.radioGroup.getChildAt(index)).setChecked(true);
        binding.radioGroup.setOnCheckedChangeListener((group, checkedID) -> {
            YearSheet.setYear(group.indexOfChild(group.findViewById(checkedID)));
            dismiss();
        });
    }

    public interface YearSheet{
        void setYear(int animeTypeIndex);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        YearSheet=(YearSheet) context;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
