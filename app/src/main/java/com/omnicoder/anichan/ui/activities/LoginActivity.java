package com.omnicoder.anichan.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.omnicoder.anichan.databinding.ActivityLoginBinding;
import com.omnicoder.anichan.utils.Constants;
import com.omnicoder.anichan.utils.LoadingDialog;
import com.omnicoder.anichan.utils.SessionManager;
import com.omnicoder.anichan.viewModels.LoginViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding;
        viewModel= new ViewModelProvider(this).get(LoginViewModel.class);
        if(sessionManager.checkLogin()){
            if(sessionManager.isTokenExpired()){
                binding = ActivityLoginBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());
                viewModel.refreshAccessToken(sessionManager.getLatestRefreshToken());
                LoadingDialog loadingDialog=new LoadingDialog(LoginActivity.this);
                loadingDialog.startLoadingForRefreshToken();
                Toast.makeText(this,"Token has expired. Getting a new one....",Toast.LENGTH_LONG).show();
                viewModel.getRefreshComplete().observe(this, success -> {
                    loadingDialog.stopLoading();
                    if(success){
                        onLoginSuccess();
                    }else{
                        Toast.makeText(LoginActivity.this, "Failed to refresh token", Toast.LENGTH_LONG).show();
                        // TODO: 01-Oct-22 Do something when things go wrong
                    }
                });

            }else{
                onLoginSuccess();
            }
        }else{
            binding = ActivityLoginBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            binding.button.setOnClickListener(v -> {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.loginUrl));
                try{
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            });
        }


        viewModel.getNewAccessToken.observe(this, success -> {
            if(success){
                onLoginSuccess();
            }else{
                Toast.makeText(this,"Token null",Toast.LENGTH_SHORT).show();
                // TODO: 01-Oct-22 Do something to help user fix the error
            }

        });



    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void parseIntentData(Uri uri){
        String code= uri.getQueryParameter("code");
        String receivedState= uri.getQueryParameter("state");
        if(code != null && receivedState.equals(LoginViewModel.STATE)){
            viewModel.getAccessToken(code);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Uri uri= getIntent().getData();
        if (uri != null && uri.toString().startsWith(Constants.ANICHAN_PAGE_LINK)) {
            parseIntentData(uri);
        }

    }

    private void onLoginSuccess() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}