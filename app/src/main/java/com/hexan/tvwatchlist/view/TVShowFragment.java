package com.hexan.tvwatchlist.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hexan.tvwatchlist.Const;
import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.model.Season;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.presenter.TVShowContract;
import com.hexan.tvwatchlist.presenter.TVShowPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TVShowFragment extends Fragment implements TVShowContract.View {
    public static final String TAG = "TVShowFragment";
    private static final String ARG_TV_SHOW = "tv_show";

    @BindView(R.id.show_banner)
    ImageView bannerImage;
    @BindView(R.id.season_list)
    LinearLayout seasonListLayout;

    private TVShowPresenter mPresenter;
    private TVShow mTVshow;

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
        mPresenter.getTVShowData(mTVshow.getTvShowId());

        return view;
    }

    @Override
    public void loadTVShow(TVShow tVShow) {
        loadSeasons(tVShow.getSeasons());
    }

    private void loadSeasons(List<Season> seasons) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        int separatorColor = ContextCompat.getColor(getContext(), R.color.separator_color);
        for (final Season season : seasons) {
            LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.item_season, seasonListLayout, false);
            TextView seasonNumber = (TextView)linearLayout.findViewById(R.id.season_number);
            seasonNumber.setText("Season " + season.getSeasonNumber());
            seasonListLayout.addView(linearLayout);
            View separator = new View(getContext());
            separator.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
            separator.setBackgroundColor(separatorColor);
            seasonListLayout.addView(separator);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO launch episode list
                }
            });
        }
    }

    @Override
    public void onError() {

    }
}
