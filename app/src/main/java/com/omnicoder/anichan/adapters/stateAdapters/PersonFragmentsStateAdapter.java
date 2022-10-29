package com.omnicoder.anichan.adapters.stateAdapters;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;



import java.util.ArrayList;

public class PersonFragmentsStateAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> fragments;

    public PersonFragmentsStateAdapter(@NonNull FragmentActivity activity,ArrayList<Fragment> fragments) {
        super(activity);
        this.fragments=fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeTabPage(int position) {
        if (!fragments.isEmpty() && position<fragments.size()) {
            fragments.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
