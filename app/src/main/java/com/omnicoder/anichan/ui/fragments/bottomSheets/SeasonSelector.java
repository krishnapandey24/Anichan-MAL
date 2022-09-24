package com.omnicoder.anichan.ui.fragments.bottomSheets;

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

public class SeasonSelector extends BottomSheetDialogFragment {
    SeasonSelectorBinding binding;
    SeasonSheet seasonSheet;
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
            seasonSheet.setSeason(group.indexOfChild(group.findViewById(checkedID)));
            dismiss();
        });
    }

    public interface SeasonSheet{
        void setSeason(int animeTypeIndex);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        seasonSheet=(SeasonSheet) context;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
