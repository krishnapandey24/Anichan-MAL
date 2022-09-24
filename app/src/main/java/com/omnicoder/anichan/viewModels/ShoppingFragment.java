package com.omnicoder.anichan.viewModels;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.fragments.ShoppingViewModel;

public class ShoppingFragment extends Fragment {

    private ShoppingViewModel mViewModel;

    public static ShoppingFragment newInstance() {
        return new ShoppingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping_fragment, container, false);
    }


}