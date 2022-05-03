package com.omnicoder.anichan.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omnicoder.anichan.Models.ExploreView;
import com.omnicoder.anichan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {
    List<ExploreView> dataHolder;
    Context context;

    public SeasonAdapter(Context context, List<ExploreView> dataHolder){
        this.dataHolder= dataHolder;
        this.context= context;
    }

    public List<ExploreView> getDataHolder() {
        return dataHolder;
    }

    public void setDataHolder(List<ExploreView> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.MyViewHolder holder, int position) {
        String title= dataHolder.get(position).getTitle();
        String imageURL= "https://image.tmdb.org/t/p/w500/"+dataHolder.get(position).getImageURL();
        holder.titleView.setText(title);
        holder.imageView.setClipToOutline(true);
        Picasso.get().load(imageURL).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView,imageView2,imageView3;
        TextView titleView,titleView2,titleView3;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageView);
            titleView= itemView.findViewById(R.id.titleView);
//            imageView2 = itemView.findViewById(R.id.imageView2);
//            titleView2= itemView.findViewById(R.id.titleView2);
//            imageView3 = itemView.findViewById(R.id.imageView3);
//            titleView3= itemView.findViewById(R.id.titleView3);
        }

        @Override
        public void onClick(View v) {
//            int position = this.getAdapterPosition();
//            ModelClass ModelClass = dataHolder.get(position);
//            int itemID = ModelClass.getId();
//            Intent ViewIntent = new Intent(context, DetailsActivity.class);
//            ViewIntent.putExtra("itemID", itemID);
//            context.startActivity(ViewIntent);


        }



    }
}


