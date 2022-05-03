package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.Crew;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.databinding.CastItemLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.MyViewHolder> {
    List<Crew> dataHolder;
    Context context;
    CastItemLayoutBinding binding;

    public CrewAdapter(Context context, List<Crew> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        binding= CastItemLayoutBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CrewAdapter.MyViewHolder holder, int position) {
        Crew crew=dataHolder.get(position);
        String imageURL= "https://image.tmdb.org/t/p/w500/"+crew.getProfile_path();
        holder.binding.titleView.setText(crew.getName());
        holder.binding.characterName.setText(crew.getJob());
        Picasso.get().load(imageURL).into(holder.binding.imageView);
        holder.binding.cardView2.setOnClickListener(v -> {
            Intent intent= new Intent(context,ViewAnimeActivity.class);
            intent.putExtra("id",crew.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CastItemLayoutBinding binding;

        public MyViewHolder(CastItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }
}


