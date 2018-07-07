package com.example.sami.popularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sami.popularmovies.R;
import com.example.sami.popularmovies.model.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private int mNumberItems;
    private ArrayList<Review> mReviewList;


    public ReviewsAdapter(ArrayList<Review> reviewList)
    {
        mReviewList= reviewList;
        mNumberItems = reviewList.size();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;
        View view = inflater.inflate(layoutID, parent, attachToParentImmediately);
        return new ReviewViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(mReviewList.get(position));
    }



    class ReviewViewHolder extends RecyclerView.ViewHolder
    {

        TextView m_tv_author, m_tv_review;

        public ReviewViewHolder( final View itemView)
        {
            super(itemView);
            m_tv_author = itemView.findViewById(R.id.tvAuthor);
            m_tv_review = itemView.findViewById(R.id.tvReview);
        }

        void bind(Review review)
        {
            m_tv_author.setText(review.getAuthor());
            m_tv_review.setText(review.getReview());
        }

    }
}
