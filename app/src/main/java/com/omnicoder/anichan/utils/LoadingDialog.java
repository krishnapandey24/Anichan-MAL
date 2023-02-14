package com.omnicoder.anichan.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.omnicoder.anichan.R;


public class LoadingDialog {
    Fragment fragment;
    Dialog dialog;
    Context context;
    Activity activity;

    public LoadingDialog(Fragment fragment,Context context){
        this.fragment=fragment;
        this.context=context;
    }

    public LoadingDialog(Activity activity){
        this.activity=activity;
    }

    public void startLoading(){
        if(dialog==null){
            dialog= new Dialog(context);
            dialog.setContentView(R.layout.loading_dialog);
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.dialog_background));
            dialog.setCancelable(false);
            Animation rotate= AnimationUtils.loadAnimation(dialog.getContext().getApplicationContext(),R.anim.rotating_animation);
            ImageView imageView=dialog.findViewById(R.id.nartuomaki);
            imageView.setAnimation(rotate);
        }
        dialog.show();    
    }

    public void startLoadingForActivity(){
        if(dialog==null){
            dialog= new Dialog(activity);
            dialog.setContentView(R.layout.loading_dialog);
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(activity,R.drawable.dialog_background));
            dialog.setCancelable(false);
            Animation rotate= AnimationUtils.loadAnimation(dialog.getContext().getApplicationContext(),R.anim.rotating_animation);
            ImageView imageView=dialog.findViewById(R.id.nartuomaki);
            imageView.setAnimation(rotate);
        }
        dialog.show();
    }

    public void startLoadingForRefreshToken(){
        if(dialog==null){
            dialog= new Dialog(activity);
            dialog.setContentView(R.layout.loading_dialog);
            dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(activity,R.drawable.dialog_background));
            dialog.setCancelable(false);
            Animation rotate= AnimationUtils.loadAnimation(dialog.getContext().getApplicationContext(),R.anim.rotating_animation);
            ImageView imageView=dialog.findViewById(R.id.nartuomaki);
            imageView.setAnimation(rotate);
            TextView textView=dialog.findViewById(R.id.message);
            textView.setText("Refreshing token \n Please wait....");
        }
        dialog.show();
    }


    public void stopLoading(){
        if(dialog==null) return;
        dialog.dismiss();
    }




}
