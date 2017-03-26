package com.hexan.tvwatchlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hexan.tvwatchlist.Const;
import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.model.Episode;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.presenter.OnTVShowClickListener;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james_000 on 3/15/2017.
 */

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private final WeakReference<Context> mContext;
    private final WeakReference<OnEpisodeClickListener> mListener;
    private final List<Episode> mEpisodes;

    public interface OnEpisodeClickListener{
        void onEpisodeClickListener(Episode episode);
    }

    public class EpisodeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.episode_number_txt)
        TextView episodeNumber;
        @BindView(R.id.episode_name_txt)
        TextView episodeName;
        @BindView(R.id.episode_watched)
        CheckBox watched;

        public EpisodeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null)
                        mListener.get().onEpisodeClickListener(mEpisodes.get(getAdapterPosition()));
                }
            });
        }

        public void setItem(Episode episode, int position) {
            episodeNumber.setText(String.format(Locale.ENGLISH, "%02d", position + 1));
            episodeName.setText(episode.getName());
        }
    }

    public EpisodeAdapter(Context context, List<Episode> episodes, OnEpisodeClickListener listener) {
        mContext = new WeakReference<>(context);
        mListener = new WeakReference<>(listener);
        this.mEpisodes = episodes;
    }

    @Override
    public EpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EpisodeViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episode, parent, false));
    }

    @Override
    public void onBindViewHolder(EpisodeViewHolder holder, int position) {
        holder.setItem(mEpisodes.get(position), position);
    }

    @Override
    public int getItemCount() {
        int nb = mEpisodes == null ? 0 : mEpisodes.size();
        return nb;
    }
}
