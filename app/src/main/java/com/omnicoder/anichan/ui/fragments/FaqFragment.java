package com.omnicoder.anichan.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.omnicoder.anichan.R;

public class FaqFragment extends Fragment {
    private TextView textView;
    private boolean isExpanded = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: 06-01-2023 Complete faq fragment

        textView = view.findViewById(R.id.text_view);

        // set the expanded text to a larger font size
        textView.setTextSize(20f);

        // set the initial text and make it ellipsized
        textView.setText("What some have have '?' as their episode number? \n \n Because we don't know the exact number of episodes the anime is going to have. \n Same goes for manga and manhwa. \n This error is not because of this app it's becuase of myanimelist");
        textView.setEllipsize(TextUtils.TruncateAt.END);

        textView.setOnClickListener(view1 -> {
            if (isExpanded) {
                textView.setMaxLines(1);
                textView.setTextSize(14f);
            } else {
                textView.setMaxLines(Integer.MAX_VALUE);
                textView.setTextSize(20f);
            }
            isExpanded = !isExpanded;
        });




    }
}