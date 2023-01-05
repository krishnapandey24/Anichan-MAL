package com.omnicoder.anichan.adapters.viewpagers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.R;
import com.omnicoder.anichan.models.responses.MainPicture;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PostersViewPagerAdapter extends RecyclerView.Adapter<PostersViewPagerAdapter.PageHolder>  {
    List<MainPicture> pictures;
    int size;

    public PostersViewPagerAdapter(List<MainPicture> pictures){
        this.pictures=pictures;
        this.size=pictures.size();
    }

    @NonNull
    @Override
    public PageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.carousel_item_layout,parent,false);
        return new PageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageHolder holder, int position) {
        try{
            Picasso.get().load(pictures.get(position).getLarge()).into(holder.imageView);
        } catch (Exception e) {
            holder.imageView.setImageResource(R.drawable.ic_no_image_placeholder);
        }
    }


    @Override
    public int getItemCount() {
        return size;
    }


    public static class PageHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public PageHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
