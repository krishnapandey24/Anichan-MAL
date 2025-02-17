package com.omnicoder.anichan.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.omnicoder.anichan.models.ExplorePlainView;

import org.jetbrains.annotations.NotNull;

public class AnimePlainComparator extends DiffUtil.ItemCallback<ExplorePlainView> {

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull ExplorePlainView oldItem, @NonNull @NotNull ExplorePlainView newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull ExplorePlainView oldItem, @NonNull @NotNull ExplorePlainView newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
}

