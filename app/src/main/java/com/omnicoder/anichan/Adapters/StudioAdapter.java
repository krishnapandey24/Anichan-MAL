package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.ProductionCompany;
import com.omnicoder.anichan.Models.Season;
import com.omnicoder.anichan.UI.Activities.ViewAnimeActivity;
import com.omnicoder.anichan.databinding.StudioItemLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StudioAdapter extends RecyclerView.Adapter<StudioAdapter.MyViewHolder> {
    List<ProductionCompany> dataHolder;
    Context context;
    StudioItemLayoutBinding binding;

    public StudioAdapter(Context context, List<ProductionCompany> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        binding=StudioItemLayoutBinding.inflate(inflater,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudioAdapter.MyViewHolder holder, int position) {
        ProductionCompany productionCompany=dataHolder.get(position);
        String imageURL= "https://image.tmdb.org/t/p/w500/"+productionCompany.getLogo_path();
        holder.binding.titleView.setText(productionCompany.getName());
        Picasso.get().load(imageURL).into(holder.binding.imageView);
        holder.binding.cardView2.setOnClickListener(v -> {
            Intent intent= new Intent(context,ViewAnimeActivity.class);
            intent.putExtra("id",productionCompany.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        StudioItemLayoutBinding binding;

        public MyViewHolder(StudioItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }
}


