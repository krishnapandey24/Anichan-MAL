package com.omnicoder.anichan.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.omnicoder.anichan.Models.ExploreView;

import org.jetbrains.annotations.NotNull;

public class AnimeComparator extends DiffUtil.ItemCallback<ExploreView> {

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull ExploreView oldItem, @NonNull @NotNull ExploreView newItem) {
            return oldItem.getAnimeID()==newItem.getAnimeID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull ExploreView oldItem, @NonNull @NotNull ExploreView newItem) {
            return oldItem.getAnimeID()==newItem.getAnimeID();
        }
}

