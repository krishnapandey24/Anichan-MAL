package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.AnimeResponse.Characters.Character;
import com.omnicoder.anichan.Models.AnimeResponse.Characters.CharacterData;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.UI.Activities.ViewCharacterActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.MyViewHolder> {
    List<CharacterData> dataHolder;
    Context context;

    public CharactersAdapter(Context context, List<CharacterData> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.cast_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersAdapter.MyViewHolder holder, int position) {
        CharacterData data= dataHolder.get(position);
        Character character=data.getCharacter();
        try{
            Picasso.get().load(character.getImages().getJpg().getImage_url()).into(holder.imageView);
        }catch (Exception e){
            //
        }
        holder.titleView.setText(character.getName());
        holder.imageView.setClipToOutline(true);
        holder.roleView.setText(data.getRole());
        holder.itemView.setOnClickListener(v -> {
            Intent intent= new Intent(context, ViewCharacterActivity.class);
            intent.putExtra("id",character.getMal_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView,roleView;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
            roleView=itemView.findViewById(R.id.characterName);
        }

    }
}


