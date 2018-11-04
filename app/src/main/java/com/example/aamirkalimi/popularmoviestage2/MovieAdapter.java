package com.example.aamirkalimi.popularmoviestage2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<MovieList> movieLists;
    private Context context;
    public MovieAdapter(List<MovieList> movieLists, Context context) {
        this.movieLists = movieLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final MovieList mList = movieLists.get(position);
        Picasso.get().load("https://image.tmdb.org/t/p/w185/" + (mList.getPosterUrl())).placeholder(R.drawable.empty).into(holder.posterUrl);
        holder.frameLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MovieList movieList = movieLists.get(position);
                Intent skipIntent = new Intent(v.getContext(), MovieDetailActivity.class);
                skipIntent.putExtra(StringUrlConstants.PARCEL_KEY, new MovieList(movieList.getId(), movieList.getTitle(), movieList.getDescription(), movieList.getPosterUrl(), movieList.getVoteAverage(), movieList.getReleaseDate()));
                v.getContext().startActivity(skipIntent);
            }
        });
    }
    public static String requestUrlForPoster(String poster) {
        Uri.Builder uriBuilder = Uri.parse("https://image.tmdb.org/t/p/w185").buildUpon().appendPath(poster);
        return uriBuilder.toString();
    }
    class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView posterUrl;
        FrameLayout frameLayout;
        private ViewHolder(View itemView) {
            super(itemView);
            posterUrl = itemView.findViewById(R.id.imageView);
            frameLayout = itemView.findViewById(R.id.frameLayoutFV);
        }
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }
    public void setMoviesLive(List<MovieList> moviesLive) {
        this.movieLists = moviesLive;
    }
    public List<MovieList> getMoviesLive() {
        return movieLists;
    }
}

