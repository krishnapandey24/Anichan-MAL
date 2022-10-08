package com.omnicoder.anichan.adapters.recyclerViews;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.models.responses.Data;
import com.omnicoder.anichan.models.responses.Node;
import com.omnicoder.anichan.R;
import com.omnicoder.anichan.ui.activities.ViewAnimeActivity;
import com.omnicoder.anichan.ui.activities.ViewMangaActivity;
import com.squareup.picasso.Picasso;

public class SearchPageAdapter extends PagingDataAdapter<Data, SearchPageAdapter.MyViewHolder> {
    public static final int LOADING_ITEM = 0;
    public static final int ANIME_ITEM = 1;
    Context context;

    public SearchPageAdapter(DiffUtil.ItemCallback<Data> diffCallBack, Context context){
        super(diffCallBack);
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.search_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchPageAdapter.MyViewHolder holder, int position) {
        Node node= getItem(position).getNode();
        if(node != null){
            String imageURL= node.getMainPicture().getMedium();
            if(imageURL!=null){
                Picasso.get().load(imageURL).into(holder.imageView);
            }
            holder.titleView.setText(node.getTitle());
            holder.imageView.setClipToOutline(true);
            holder.itemView.setOnClickListener(v -> {
                Intent intent;
                if(node.getMedia_type().equals("manga")){
                    intent = new Intent(context, ViewMangaActivity.class);
                }else{
                    intent = new Intent(context, ViewAnimeActivity.class);
                    intent.putExtra("media_type",node.getMedia_type());

                }
                intent.putExtra("id",node.getId());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
       return position== getItemCount() ? ANIME_ITEM : LOADING_ITEM;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView titleView;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
        }
    }

}


