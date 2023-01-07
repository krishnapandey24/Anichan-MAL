package com.omnicoder.anichan.utils;

import static com.omnicoder.anichan.utils.Constants.FIRST_LIST_ITEM_SPACE_HEIGHT;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListItemHorizontalDecor extends RecyclerView.ItemDecoration{
    int height;

    public ListItemHorizontalDecor(int height){
        this.height=height;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view)==0) {
            outRect.left = FIRST_LIST_ITEM_SPACE_HEIGHT;
            outRect.top = height;
            outRect.right = height;
            outRect.bottom = height;
        }




    }
}
