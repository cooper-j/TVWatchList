package com.hexan.tvwatchlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hexan.tvwatchlist.Const;
import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.presenter.OnTVShowClickListener;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james_000 on 3/15/2017.
 */

public class TVShowSearchAdapter extends RecyclerView.Adapter<TVShowSearchAdapter.TVShowViewHolder> {

    private final WeakReference<Context> mContext;
    private final WeakReference<OnTVShowClickListener> mListener;
    private final List<TVShow> mTVShows;

    public class TVShowViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.show_image)
        ImageView showImage;
        @BindView(R.id.show_name)
        TextView showName;
        @BindView(R.id.show_desc)
        TextView showDescription;

        public TVShowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null)
                        mListener.get().onTVShowClick(mTVShows.get(getAdapterPosition()));
                }
            });
        }

        public void setItem(TVShow tvShow) {
            showName.setText(tvShow.getName());
            showDescription.setText("First Air Date: " + (tvShow.getFirstAirDate() != null ? tvShow.getFirstAirDate() : "Not Available"));

            if (mContext != null)
                Picasso.with(mContext.get()).load(Const.IMG_URL + tvShow.getPosterPath())
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_error)
                        .into(showImage);
        }
    }

    public TVShowSearchAdapter(Context context, List<TVShow> tvShows, OnTVShowClickListener listener) {
        mContext = new WeakReference<>(context);
        mListener = new WeakReference<>(listener);
        this.mTVShows = tvShows;
    }

    @Override
    public TVShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TVShowViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tvshow, parent, false));
    }

    @Override
    public void onBindViewHolder(TVShowViewHolder holder, int position) {
        holder.setItem(mTVShows.get(position));
    }

    @Override
    public int getItemCount() {
        return mTVShows == null ? 0 : mTVShows.size();
    }

    public void addItem(TVShow tvShow) {
        mTVShows.add(tvShow);
        notifyItemInserted(mTVShows.size());
    }
}
