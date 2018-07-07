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
import com.example.sami.popularmovies.model.Trailer;
import com.example.sami.popularmovies.utils.ListItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private int mNumberItems;
    private ArrayList<Trailer> mTrailerList;
    private ListItemClickListener mOnClickListener;


    public TrailersAdapter(ArrayList<Trailer> trailerList, ListItemClickListener listener)
    {
        mTrailerList = trailerList;
        mNumberItems = trailerList.size();
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;
        View view = inflater.inflate(layoutID, parent, attachToParentImmediately);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.bind(mTrailerList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }



    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        ImageView m_iv_trailerPoster;
        TextView m_tv_trailerName, m_tv_trailerLang, m_tv_trailerType;

        public TrailerViewHolder( final View itemView)
        {
            super(itemView);
            m_iv_trailerPoster = itemView.findViewById(R.id.ivTrailerImage);
            m_tv_trailerLang = itemView.findViewById(R.id.tvTrailerLang);
            m_tv_trailerName = itemView.findViewById(R.id.tvTrailerName);
            m_tv_trailerType = itemView.findViewById(R.id.tvTrailerType);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        void bind(Trailer trailer)
        {
            String imagePath = "https://img.youtube.com/vi/"+trailer.getKey()+"/default.jpg";
            Picasso.get().load(imagePath).fit()
                    .error(R.drawable.error)
                    .placeholder(R.drawable.placeholder)
                    .into(m_iv_trailerPoster);
            m_tv_trailerName.setText(trailer.getName());
            m_tv_trailerLang.setText(trailer.getLanguage());
            m_tv_trailerType.setText(trailer.getType());
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }


        @Override
        public boolean onLongClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onLongClick(clickedPosition);
            return true;
        }
    }
}
