package com.hexan.tvwatchlist.presenter.season;

import com.hexan.tvwatchlist.model.Season;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.network.TheMovieDBAPI;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by james_000 on 3/15/2017.
 */

public class SeasonPresenter implements SeasonContract.Presenter {
    private WeakReference<SeasonContract.View> mView;
    final TheMovieDBAPI mTheMovieDBAPI;

    public SeasonPresenter(SeasonContract.View view) {
        mView = new WeakReference<>(view);
        mTheMovieDBAPI = TheMovieDBAPI.retrofit.create(TheMovieDBAPI.class);
    }

    @Override
    public void loadSeasonData(final Season season) {
        final Call<Season> callSeason = mTheMovieDBAPI.getSeason(season.getTvShow().getTvShowId(), season.getSeasonNumber());
        callSeason.enqueue(new Callback<Season>() {
            @Override
            public void onResponse(Call<Season> call, Response<Season> response) {
                if (mView != null) {
                    if (response.isSuccessful()) {
                        mView.get().displaySeason(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Season> call, Throwable t){
            }
        });
    }
}
