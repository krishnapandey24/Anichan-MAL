package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.AnimeResponse.Characters.Character;
import com.omnicoder.anichan.Models.AnimeResponse.Staff.StaffData;
import com.omnicoder.anichan.UI.Activities.ViewPersonActivity;
import com.omnicoder.anichan.databinding.CastItemLayoutBinding;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.MyViewHolder> {
    List<StaffData> dataHolder;
    Context context;
    CastItemLayoutBinding binding;

    public CrewAdapter(Context context, List<StaffData> dataHolder){
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
        StaffData staffData=dataHolder.get(position);
        Character person=staffData.getPerson();
        holder.binding.titleView.setText(person.getName());
        holder.binding.characterName.setText(getPositions(staffData.getPositions()));
        try{
            Picasso.get().load(person.getImages().getJpg().getImage_url()).into(holder.binding.imageView);
        }catch (Exception e){
            //
        }
        holder.binding.cardView2.setOnClickListener(v -> {
            Intent intent= new Intent(context, ViewPersonActivity.class);
            intent.putExtra("id",person.getMal_id());
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

    private StringBuilder getPositions(List<String> genres) {
        int size=genres.size()-1;
        StringBuilder studiosString = new StringBuilder();
        int i = 0;
        while (i < size - 1) {
            studiosString.append(genres.get(i));
            studiosString.append(",");
            i++;
        }
        studiosString.append(genres.get(i));
        return studiosString;
    }
}


