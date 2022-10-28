package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {
    List<JikanSubEntity> dataHolder;
    Context context;
    int size;

    public ScheduleAdapter(Context context, List<JikanSubEntity> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
        this.size=dataHolder.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.layout2,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.MyViewHolder holder, int position) {
        JikanSubEntity anime= dataHolder.get(size-position-1);
        String title= anime.getTitle();
        String imageURL= anime.getImages().getJpg().getImage_url();
        Picasso.get().load(imageURL).into(holder.imageView);
        holder.titleView.setText(title);
        holder.imageView.setClipToOutline(true);
        holder.constraintLayout.setOnClickListener(v -> {
            Intent intent= new Intent(context, ViewAnimeActivity.class);
            intent.putExtra("media_type","anime");
            intent.putExtra("id",anime.getMalId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleView;
        ImageView imageView;
        ConstraintLayout constraintLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            titleView=itemView.findViewById(R.id.titleView);
            imageView=itemView.findViewById(R.id.imageView);
            constraintLayout=itemView.findViewById(R.id.cardView2);

        }

    }

}


