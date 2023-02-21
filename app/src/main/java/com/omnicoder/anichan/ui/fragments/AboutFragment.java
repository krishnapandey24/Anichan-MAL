package com.omnicoder.anichan.ui.fragments;

import static com.omnicoder.anichan.utils.Constants.EMAIL;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.FragmentAboutBinding;


public class AboutFragment extends Fragment {
    FragmentAboutBinding binding;
    private Dialog developerDialog =null;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentAboutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.developer.setOnClickListener(v -> launchDeveloperDialog());
        binding.librariesUsed.setOnClickListener(v -> startActivity(new Intent(getContext(), OssLicensesMenuActivity.class)));
        binding.changeLog.setOnClickListener(v-> Toast.makeText(getContext(),"No changes yet. Please wait for the update",Toast.LENGTH_SHORT).show());



    }

    @SuppressLint("NonConstantResourceId")
    private void launchDeveloperDialog() {
        if(developerDialog ==null){
            developerDialog = new Dialog(getContext());
            developerDialog.setContentView(R.layout.developer_dialog);
            developerDialog.setCancelable(true);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(developerDialog.getWindow().getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            TextView email=developerDialog.findViewById(R.id.email);
            email.setOnClickListener(v -> {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",EMAIL, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact:Developer");
                startActivity(Intent.createChooser(emailIntent, ""));
            });

            developerDialog.getWindow().setAttributes(layoutParams);
        }
        developerDialog.show();
    }
}