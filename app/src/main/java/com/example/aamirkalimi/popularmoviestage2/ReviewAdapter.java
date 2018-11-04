package com.example.aamirkalimi.popularmoviestage2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ReviewList> reviewsLists;
    private Context context;
    public ReviewAdapter(List<ReviewList> reviewsLists, Context context){
        this.reviewsLists = reviewsLists;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ReviewList reviewsList = reviewsLists.get(position);
        holder.authorTV.setText(reviewsList.getAuthor());
        holder.reviewTV.setText(reviewsList.getReviewContent());
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView authorTV, reviewTV;
        ViewHolder(View itemView) {
            super(itemView);
            authorTV = itemView.findViewById(R.id.authorNameTv);
            reviewTV = itemView.findViewById(R.id.contentTv);
        }
    }

    @Override
    public int getItemCount() {
        return reviewsLists.size();
    }
}
