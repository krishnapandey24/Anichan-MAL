package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.databinding.FriendsItemLayoutBinding;
import com.omnicoder.anichan.models.jikan.FriendData;
import com.omnicoder.anichan.models.jikan.UserFriend;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {
    List<FriendData> dataHolder;
    Context context;
    ViewUserInterface viewUserInterface;
    FriendsItemLayoutBinding binding;

    public FriendsAdapter(Context context, List<FriendData> dataHolder, ViewUserInterface viewUserInterface){
        this.dataHolder= dataHolder;
        this.context= context;
        this.viewUserInterface=viewUserInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        binding= FriendsItemLayoutBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.MyViewHolder holder, int position) {
        UserFriend user=dataHolder.get(position).getUser();
        String username=user.getUsername();
        holder.binding.titleView.setText(username);
        try{
            Picasso.get().load(user.getImages().getJpg().getImage_url()).into(holder.binding.imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.binding.cardView2.setOnClickListener(v -> viewUserInterface.viewUser(username));
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        FriendsItemLayoutBinding binding;

        public MyViewHolder(FriendsItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }


    public interface ViewUserInterface{
        void viewUser(String username);
    }



}


