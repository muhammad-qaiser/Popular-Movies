package com.example.sami.popularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sami.popularmovies.R;
import com.example.sami.popularmovies.model.Movie;
import com.example.sami.popularmovies.utils.ListItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviesAdapter  extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{

    private int mNumberItems;
    private List<Movie> mMoviesList;
    final private ListItemClickListener mOnClickListener;


    public MoviesAdapter(ListItemClickListener listener)
    {
        mOnClickListener = listener;
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.mMoviesList = moviesList;
        mNumberItems = moviesList.size();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;
        View view = inflater.inflate(layoutID, parent, attachToParentImmediately);
        return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(mMoviesList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView m_iv_poster;
        TextView m_tv_title, m_tv_language;
        String posterPath= "http://image.tmdb.org/t/p/w185/";

        public MovieViewHolder( final View itemView)
        {
            super(itemView);
            m_iv_poster = itemView.findViewById(R.id.iv_poster);
            m_tv_title = itemView.findViewById(R.id.tv_title);
            m_tv_language = itemView.findViewById(R.id.tv_language);
            itemView.setOnClickListener(this);
        }

        void bind(Movie  movie)
        {
            Picasso.get().load(posterPath + movie.mPosterPath).fit()
                    .error(R.drawable.error)
                    .placeholder(R.drawable.placeholder)
                    .into(m_iv_poster);
            m_tv_language.setText(movie.mLanguage);
            m_tv_title.setText(movie.mTitle);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
