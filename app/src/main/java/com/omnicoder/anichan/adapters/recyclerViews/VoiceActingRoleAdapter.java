package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.databinding.VoiceActingRoleItemLayoutBinding;
import com.omnicoder.anichan.models.jikan.VoiceActingRole;
import com.omnicoder.anichan.models.jikan.JikanSubEntity;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.ui.activities.ViewCharacterActivity;
import com.omnicoder.anichan.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VoiceActingRoleAdapter extends RecyclerView.Adapter<VoiceActingRoleAdapter.MyViewHolder> {
    List<VoiceActingRole> dataHolder;
    Context context;
    VoiceActingRoleItemLayoutBinding binding;

    public VoiceActingRoleAdapter(Context context, List<VoiceActingRole> dataHolder) {
        this.dataHolder = dataHolder;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = VoiceActingRoleItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VoiceActingRoleAdapter.MyViewHolder holder, int position) {
        VoiceActingRole voiceActorRole = dataHolder.get(position);
        JikanSubEntity anime = voiceActorRole.getAnime();
        JikanSubEntity character = voiceActorRole.getCharacter();
        try {
            Picasso.get().load(anime.getImages().getJpg().getImage_url()).into(holder.binding.animePoster);
        } catch (Exception e) {
            holder.binding.animePoster.setImageResource(R.drawable.ic_no_image_placeholder);
        }
        try {
            Picasso.get().load(character.getImages().getJpg().getImage_url()).into(holder.binding.characterImageView);
        } catch (Exception e) {
            holder.binding.characterImageView.setImageResource(R.drawable.ic_no_image_placeholder);
        }
        holder.binding.animeTitle.setText(anime.getTitle());
        holder.binding.animePoster.setClipToOutline(true);
        holder.binding.characterName.setText(character.getTitle());
        holder.binding.role.setText(voiceActorRole.getRole());
        holder.binding.animePoster.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewAnimeActivity.class);
            intent.putExtra(Constants.ID, anime.getMalId());
            context.startActivity(intent);
        });
        holder.binding.characterImageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewCharacterActivity.class);
            intent.putExtra(Constants.ID, character.getMalId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        VoiceActingRoleItemLayoutBinding binding;

        public MyViewHolder(@NonNull VoiceActingRoleItemLayoutBinding layoutBinding) {
            super(layoutBinding.getRoot());
            this.binding = layoutBinding;
        }

    }
}


