package com.hexan.tvwatchlist.presenter;

import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.network.TheMovieDBAPI;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by james_000 on 3/15/2017.
 */

public class TVShowPresenter implements TVShowContract.Presenter {
    private WeakReference<TVShowContract.View> mView;
    final TheMovieDBAPI mTheMovieDBAPI;

    public TVShowPresenter(TVShowContract.View view) {
        mView = new WeakReference<>(view);
        mTheMovieDBAPI = TheMovieDBAPI.retrofit.create(TheMovieDBAPI.class);
    }

    @Override
    public void getTVShowData(int tvShowId) {
        final Call<TVShow> callTvShow = mTheMovieDBAPI.getTVShow(tvShowId);
        callTvShow.enqueue(new Callback<TVShow>() {
            @Override
            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                if (mView.get() != null)
                    mView.get().loadTVShow(response.body());
            }

            @Override
            public void onFailure(Call<TVShow> call, Throwable t) {
                if (mView.get() != null)
                    mView.get().onError();
            }
        });
    }
}
