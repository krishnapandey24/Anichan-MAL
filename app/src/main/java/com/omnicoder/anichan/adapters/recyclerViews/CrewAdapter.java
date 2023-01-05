package com.omnicoder.anichan.adapters.recyclerViews;

import static com.omnicoder.anichan.utils.Constants.ID;
import static com.omnicoder.anichan.utils.Constants.RECYCLER_VIEW_MAX_LIMIT;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.models.animeResponse.Characters.Character;
import com.omnicoder.anichan.models.animeResponse.Staff.StaffData;
import com.omnicoder.anichan.databinding.CastItemLayoutBinding;
import com.omnicoder.anichan.ui.activities.ViewPersonActivity;
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
            holder.binding.imageView.setImageResource(R.drawable.ic_no_image_placeholder);
        }
        holder.binding.cardView2.setOnClickListener(v -> {
            Intent intent= new Intent(context, ViewPersonActivity.class);
            intent.putExtra(ID,person.getMal_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(dataHolder.size(),RECYCLER_VIEW_MAX_LIMIT);
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


