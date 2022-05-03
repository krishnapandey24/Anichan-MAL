package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.Cast;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.databinding.CastItemLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {
    List<Map<String,Object>> dataHolder;
    Context context;
    CastItemLayoutBinding binding;

    public CastAdapter(Context context, List<Map<String,Object>> dataHolder){
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
    public void onBindViewHolder(@NonNull CastAdapter.MyViewHolder holder, int position) {
        Map<String,Object> cast=dataHolder.get(position);
        String imageURL= "https://image.tmdb.org/t/p/w500/"+ cast.get("profile_path");
        String characterName=(String)cast.get("character");
        holder.binding.titleView.setText((String)cast.get("name"));
        holder.binding.characterName.setText(characterName.replace("(voice)",""));
        Picasso.get().load(imageURL).into(holder.binding.imageView);
        holder.binding.cardView2.setOnClickListener(v -> {
            Intent intent= new Intent(context,ViewAnimeActivity.class);
            intent.putExtra("id",(Integer) cast.get("id"));
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


