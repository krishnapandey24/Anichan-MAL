package com.omnicoder.anichan.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.omnicoder.anichan.Models.Animes;

import org.jetbrains.annotations.NotNull;

public class SearchPageComparator extends DiffUtil.ItemCallback<Animes> {

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Animes oldItem, @NonNull @NotNull Animes newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Animes oldItem, @NonNull @NotNull Animes newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
}

