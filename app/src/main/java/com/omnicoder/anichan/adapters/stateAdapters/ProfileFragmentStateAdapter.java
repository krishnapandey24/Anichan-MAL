package com.omnicoder.anichan.adapters.stateAdapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.omnicoder.anichan.models.jikan.Favorites;
import com.omnicoder.anichan.models.jikan.JikanUserStatistic;
import com.omnicoder.anichan.ui.fragments.profile.FavoritesFragment;
import com.omnicoder.anichan.ui.fragments.profile.FriendsFragment;
import com.omnicoder.anichan.ui.fragments.profile.StatsFragment;
import com.omnicoder.anichan.viewModels.ProfileViewModel;


public class ProfileFragmentStateAdapter extends FragmentStateAdapter {
    StatsFragment statsFragment;
    FavoritesFragment favoritesFragment;
    FriendsFragment friendsFragment;
    JikanUserStatistic statistics;
    Favorites favorites;
    String username;
    ProfileViewModel viewModel;
    public ProfileFragmentStateAdapter(@NonNull Fragment fragment, JikanUserStatistic statistics, Favorites favorites, String username, ProfileViewModel viewModel) {
        super(fragment);
        this.statistics=statistics;
        this.favorites=favorites;
        this.username=username;
        this.viewModel=viewModel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            if(statsFragment==null){
                statsFragment=new StatsFragment(statistics);
            }
            return statsFragment;
        }else if(position==1){
            if(favoritesFragment==null){
                favoritesFragment=new FavoritesFragment(favorites);
            }
            return favoritesFragment;
        }else{
            if(friendsFragment==null){
                friendsFragment=FriendsFragment.newInstance(username);
            }
            return friendsFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
