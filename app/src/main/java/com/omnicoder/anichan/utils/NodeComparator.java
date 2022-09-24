package com.omnicoder.anichan.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.omnicoder.anichan.models.responses.Data;

import org.jetbrains.annotations.NotNull;

public class NodeComparator extends DiffUtil.ItemCallback<Data> {

        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Data oldItem, @NonNull @NotNull Data newItem) {
            return oldItem.getNode().getId()==newItem.getNode().getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Data oldItem, @NonNull @NotNull Data newItem) {
            return oldItem.getNode().getId()==newItem.getNode().getId();
        }
}

