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
import com.omnicoder.anichan.databinding.TopChartMenuBinding;

import org.jetbrains.annotations.NotNull;

public class AnimeChartBottomSheet extends BottomSheetDialogFragment {
    TopChartMenuBinding binding;
    AnimeChartSheet animeChartSheet;
    public int index=0;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding= TopChartMenuBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((RadioButton)binding.radioGroup.getChildAt(index)).setChecked(true);
        binding.radioGroup.setOnCheckedChangeListener((group, checkedID) -> {
            animeChartSheet.changeList(group.indexOfChild(group.findViewById(checkedID)));
            dismiss();
        });
    }

    public interface AnimeChartSheet{
        void changeList(int animeTypeIndex);
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        animeChartSheet=(AnimeChartSheet) context;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
