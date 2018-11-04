package com.example.aamirkalimi.popularmoviestage2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter <TrailerAdapter.ViewHolder> {
    private static final String TAG = "Intent-Tag";
    private List<TrailerList> trailerLists;
    private Context context;
    private final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%s/0.jpg";
    public TrailerAdapter() { }
    public TrailerAdapter(List<TrailerList> trailerLists, Context context){
        this.trailerLists = trailerLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final TrailerList trailersList = trailerLists.get(holder.getAdapterPosition());
        Picasso.get().load(String.format(YOUTUBE_THUMBNAIL_URL, trailerLists.get(position).getTrailerKey())).placeholder(R.drawable.youtube).into(holder.viewTrailer);
        holder.trailerRelative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TrailerList trailerList = trailerLists.get(holder.getAdapterPosition());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailerList.getTrailerKey()));
                PackageManager packageManager = v.getContext().getPackageManager();
                if (intent.resolveActivity(packageManager) != null)
                    v.getContext().startActivity(intent);
                else
                    Log.d(TAG, "No Intent available to handle action!");
                Toast.makeText(v.getContext(), trailerList.getTrailerKey(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView viewTrailer;
        RelativeLayout trailerRelative;
        ViewHolder(View itemView) {
            super(itemView);
            viewTrailer = itemView.findViewById(R.id.videoViewTrailer);
            trailerRelative = itemView.findViewById(R.id.relativeLayoutRV2);
        }
    }

    @Override
    public int getItemCount() {
        return trailerLists.size();
    }
}
