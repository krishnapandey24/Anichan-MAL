package com.omnicoder.anichan.adapters.recyclerViews;

import static com.omnicoder.anichan.utils.Constants.RECYCLER_VIEW_MAX_LIMIT;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.CastItemLayoutBinding;
import com.omnicoder.anichan.models.jikan.CharacterVoiceActor;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.ui.activities.ViewPersonActivity;
import com.omnicoder.anichan.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VoiceActorsAdapter extends RecyclerView.Adapter<VoiceActorsAdapter.MyViewHolder> {
    List<CharacterVoiceActor> dataHolder;
    Context context;
    CastItemLayoutBinding binding;

    public VoiceActorsAdapter(Context context, List<CharacterVoiceActor> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding= CastItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceActorsAdapter.MyViewHolder holder, int position) {
        CharacterVoiceActor voiceActor=dataHolder.get(position);
        JikanSubEntity entity=voiceActor.getPerson();
        String title= entity.getTitle();
        try{
            Picasso.get().load(entity.getImages().getJpg().getImage_url()).into(holder.binding.imageView);
        } catch (Exception e) {
            holder.binding.imageView.setImageResource(R.drawable.ic_no_image_placeholder);
        }
        holder.binding.titleView.setText(title);
        holder.binding.imageView.setClipToOutline(true);
        holder.binding.characterName.setText(voiceActor.getLanguage());
        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent=new Intent(context, ViewPersonActivity.class);
            intent.putExtra(Constants.ID,entity.getMalId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(dataHolder.size(),RECYCLER_VIEW_MAX_LIMIT);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CastItemLayoutBinding binding;

        public MyViewHolder(@NonNull CastItemLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            this.binding= layoutBinding;
        }




    }
}


