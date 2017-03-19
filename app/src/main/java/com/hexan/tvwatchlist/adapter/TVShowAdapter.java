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
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james_000 on 3/15/2017.
 */

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {

    private final WeakReference<Context> mContext;
    private final List<TVShow> mTVShows;

    public class TVShowViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.show_image)
        ImageView showImage;
        @BindView(R.id.show_name)
        TextView showName;

        public TVShowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItem(TVShow tvShow) {
            showName.setText(tvShow.getName());

            if (mContext.get() != null)
                Picasso.with(mContext.get()).load(Const.IMG_URL + tvShow.getPosterPath())
                        .placeholder(R.drawable.image_placeholder)
                        .error(R.drawable.image_error)
                        .into(showImage);
        }
    }

    public TVShowAdapter(Context context, List<TVShow> tvShows) {
        mContext = new WeakReference<>(context);
        this.mTVShows = tvShows;
    }

    @Override
    public TVShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TVShowViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tvshow_poster, parent, false));
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
