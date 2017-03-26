package com.hexan.tvwatchlist.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.hexan.tvwatchlist.adapter.EpisodeAdapter;
import com.hexan.tvwatchlist.model.Episode;
import com.hexan.tvwatchlist.model.Season;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.presenter.main.MainContract;
import com.hexan.tvwatchlist.presenter.season.SeasonContract;
import com.hexan.tvwatchlist.presenter.season.SeasonPresenter;
import com.hexan.tvwatchlist.presenter.tvshow.TVShowContract;
import com.hexan.tvwatchlist.presenter.tvshow.TVShowPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class SeasonFragment extends Fragment implements SeasonContract.View, EpisodeAdapter.OnEpisodeClickListener {
    public static final String TAG = "SeasonFragment";
    private static final String ARG_SEASON = "season";

    @BindView(R.id.episode_list)
    RecyclerView episodeList;

    private SeasonPresenter mPresenter;
    private Season mSeason;
    private MainContract mListener;

    public SeasonFragment() {
    }

    public static SeasonFragment newInstance(Season season) {
        SeasonFragment fragment = new SeasonFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SEASON, season);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSeason = getArguments().getParcelable(ARG_SEASON);
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
        View view = inflater.inflate(R.layout.fragment_season, container, false);
        ButterKnife.bind(this, view);
        //TODO get season prior to showing activity as season names not in getTVShow
        //mListener.onUpdateTileSubtitle(mSeason.getTvShow().getName(), mSeason.getName());

        mPresenter = new SeasonPresenter(this);
        mPresenter.loadSeasonData(mSeason);
        return view;
    }

    @Override
    public void displaySeason(Season season) {
        season.setTvShow(mSeason.getTvShow());
        mSeason = season;
        episodeList.setLayoutManager(new LinearLayoutManager(getContext()));
        episodeList.setAdapter(new EpisodeAdapter(getContext(), season.getEpisodes(), this));
    }

    @Override
    public void onError() {

    }

    @Override
    public void onEpisodeClickListener(Episode episode) {

    }
}
