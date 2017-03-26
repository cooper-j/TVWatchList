package com.hexan.tvwatchlist.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hexan.tvwatchlist.Const;
import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.model.Season;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.presenter.main.MainContract;
import com.hexan.tvwatchlist.presenter.tvshow.TVShowContract;
import com.hexan.tvwatchlist.presenter.tvshow.TVShowPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class TVShowFragment extends Fragment implements TVShowContract.View {
    public static final String TAG = "TVShowFragment";
    private static final String ARG_TV_SHOW = "tv_show";

    @BindView(R.id.show_banner)
    ImageView bannerImage;
    @BindView(R.id.follow_show)
    AppCompatCheckBox followCheckBox;
    @BindView(R.id.loading_data)
    FrameLayout loadingDataLayout;
    @BindView(R.id.show_content)
    LinearLayout showContentListLayout;
    @BindView(R.id.season_list)
    LinearLayout seasonListLayout;
    @BindView(R.id.overview_text)
    TextView overviewTextView;
    @BindView(R.id.genres_text)
    TextView genresTextView;
    @BindView(R.id.first_air_text)
    TextView firstAirTextView;
    @BindView(R.id.status_text)
    TextView statusTextView;
    @BindView(R.id.nb_episodes_text)
    TextView nbEpisodeTextView;
    @BindView(R.id.avg_score_text)
    TextView averageScoreTextView;


    private TVShowPresenter mPresenter;
    private TVShow mTVshow;
    private MainContract mListener;

    public TVShowFragment() {
    }

    public static TVShowFragment newInstance(TVShow tvShow) {
        TVShowFragment fragment = new TVShowFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TV_SHOW, tvShow);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTVshow = getArguments().getParcelable(ARG_TV_SHOW);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (MainContract) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MainContract");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        ButterKnife.bind(this, view);
        if (mTVshow.getPosterPath() != null || mTVshow.getBackdropPath() != null)
            Picasso.with(getContext()).load(Const.IMG_URL + (mTVshow.getBackdropPath() != null ? mTVshow.getBackdropPath() : mTVshow.getPosterPath()))
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_error)
                    .into(bannerImage);

        mPresenter = new TVShowPresenter(this);
        loadingDataLayout.setVisibility(View.VISIBLE);
        showContentListLayout.setVisibility(View.GONE);

        if (mListener != null)
            mListener.onUpdateTile(mTVshow.getName());
        followCheckBox.setChecked(mTVshow.isFollowing());
        mPresenter.loadTVShowData(mTVshow.getTvShowId());

        return view;
    }

    @Override
    public void displayTVShow(TVShow tVShow) {
        mTVshow = tVShow;
        loadingDataLayout.setVisibility(View.GONE);

        overviewTextView.setText(tVShow.getOverview());
        firstAirTextView.setText(tVShow.getFirstAirDate());
        statusTextView.setText(tVShow.getStatus());
        nbEpisodeTextView.setText(String.valueOf(tVShow.getNumberOfEpisodes()));
        averageScoreTextView.setText(String.format(Locale.ENGLISH, "%.1f/10", tVShow.getVoteAverage()));

        followCheckBox.setChecked(tVShow.isFollowing());
        loadSeasons(tVShow.getSeasons());
        showContentListLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayTVShowGenres(String genres) {
        genresTextView.setText(genres);
    }

    @Override
    public void setIsFollowing(boolean isFollowing) {
        followCheckBox.setChecked(isFollowing);
    }

    private void loadSeasons(List<Season> seasons) {
        if (getContext() != null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            for (final Season season : seasons) {
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.item_season, seasonListLayout, false);
                TextView seasonNumber = (TextView) linearLayout.findViewById(R.id.season_number);
                seasonNumber.setText("Season " + season.getSeasonNumber());
                seasonListLayout.addView(linearLayout);
                View separator = new View(getContext());
                separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getContext().getResources().getDimensionPixelSize(R.dimen.separator_height)));
                separator.setBackgroundResource(R.color.separator_color);
                seasonListLayout.addView(separator);

                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO launch episode list
                    }
                });
            }
        }
    }

    @Override
    public void onError() {
        loadingDataLayout.setVisibility(View.GONE);
    }

    @OnCheckedChanged(R.id.follow_show)
    void onFollowChanged(CompoundButton checkBox, boolean isChecked) {
        if (mTVshow.isFollowing() != isChecked)
            mPresenter.updateTVShow(mTVshow, isChecked);
    }
}
