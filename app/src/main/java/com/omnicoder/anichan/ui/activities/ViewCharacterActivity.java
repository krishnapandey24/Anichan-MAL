package com.omnicoder.anichan.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.omnicoder.anichan.databinding.ActivityViewCharacterBinding;

public class ViewCharacterActivity extends AppCompatActivity {
    ActivityViewCharacterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewCharacterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: 29-Sep-22 Complete ViewCharacterActivity 
    }
}