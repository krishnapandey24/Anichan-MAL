package com.omnicoder.anichan.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.omnicoder.anichan.R;

public class AccountListItemView extends ConstraintLayout {


    public AccountListItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.account_list_item_view,this);
        TextView titleView=findViewById(R.id.account_list_title);
        TextView descriptionView=findViewById(R.id.account_list_description);
        ImageView iconView=findViewById(R.id.account_list_icon);
        TypedArray attributes=context.obtainStyledAttributes(attrs,R.styleable.AccountListItemView,defStyleAttr,0);
        titleView.setText(attributes.getText(R.styleable.AccountListItemView_item_title));
        descriptionView.setText(attributes.getText(R.styleable.AccountListItemView_item_description));
        iconView.setImageDrawable(attributes.getDrawable(R.styleable.AccountListItemView_item_icon));
        attributes.recycle();
    }
}
