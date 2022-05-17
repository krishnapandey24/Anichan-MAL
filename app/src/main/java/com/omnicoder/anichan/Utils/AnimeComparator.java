package com.omnicoder.anichan.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.omnicoder.anichan.Models.Responses.Data;
import com.omnicoder.anichan.Models.Responses.Node;

import org.jetbrains.annotations.NotNull;

public class AnimeComparator extends DiffUtil.ItemCallback<Data> {

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Data oldItem, @NonNull @NotNull Data newItem) {
            return oldItem.getNode().getId()==newItem.getNode().getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Data oldItem, @NonNull @NotNull Data newItem) {
            return oldItem.getNode().getId()==newItem.getNode().getId();
        }
}

