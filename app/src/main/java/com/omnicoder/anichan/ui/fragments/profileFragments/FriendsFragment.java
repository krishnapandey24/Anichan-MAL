package com.omnicoder.anichan.ui.fragments.profileFragments;

import static com.omnicoder.anichan.utils.Constants.USERNAME;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.omnicoder.anichan.NavGraphDirections;
import com.omnicoder.anichan.adapters.FriendsAdapter;
import com.omnicoder.anichan.databinding.FragmentFriendsBinding;
import com.omnicoder.anichan.models.jikan.FriendData;
import com.omnicoder.anichan.ui.activities.MainActivity;
import com.omnicoder.anichan.viewModels.ProfileViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FriendsFragment extends Fragment implements FriendsAdapter.ViewUserInterface {
    FragmentFriendsBinding binding;
    String username;
    ProfileViewModel viewModel;


    public static FriendsFragment newInstance(String username){
        FriendsFragment friendsFragment=new FriendsFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        friendsFragment.setArguments(args);
        return friendsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            String savedUsername=savedInstanceState.getString(USERNAME);
            if(savedUsername!=null){
                username=savedUsername;
            }
        }else{
            if(getArguments()!=null){
                viewModel=new ViewModelProvider(this).get(ProfileViewModel.class);
                username=getArguments().getString(USERNAME);
            }
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(USERNAME,username);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentFriendsBinding.inflate(inflater,container,false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.fetchUserFriends(username);
        viewModel.getUserFriends().observe(getViewLifecycleOwner(), userFriendResponse -> setRecyclerView(userFriendResponse.getData()));

    }

    public void setRecyclerView(List<FriendData> friends){
        FriendsAdapter adapter=new FriendsAdapter(getContext(),friends,this);
        binding.recyclerView.setAdapter(adapter);
    }


    @Override
    public void viewUser(String username) {
        Navigation.findNavController(binding.getRoot()).navigate(NavGraphDirections.moveToParentFragment(username));
    }
}