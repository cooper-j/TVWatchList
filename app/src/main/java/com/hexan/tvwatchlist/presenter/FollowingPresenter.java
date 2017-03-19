package com.hexan.tvwatchlist.presenter;

import android.support.annotation.NonNull;

import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.model.TVShowSearch;
import com.hexan.tvwatchlist.network.TheMovieDBAPI;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by james_000 on 3/15/2017.
 */

public class FollowingPresenter implements FollowingContract.Presenter {
    private WeakReference<FollowingContract.View> mView;
    final TheMovieDBAPI mTheMovieDBAPI;

    public FollowingPresenter(FollowingContract.View view) {
        mView = new WeakReference<>(view);
        mTheMovieDBAPI = TheMovieDBAPI.retrofit.create(TheMovieDBAPI.class);
    }

    @Override
    public void loadFollowingTVShows() {

        SQLite.select()
                .from(TVShow.class)
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<TVShow>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<TVShow> tResult) {
                        //if (mView.get() != null)
                            //mView.get().setTVShowList(tResult);
                    }
                }).execute();

        final Call<TVShowSearch> call = mTheMovieDBAPI.searchTVShows("games", 1);

        call.enqueue(new Callback<TVShowSearch>() {
            @Override
            public void onResponse(Call<TVShowSearch> call, Response<TVShowSearch> response) {
                if (response.isSuccessful() && mView.get() != null) {
                    getTVShows(response.body());
                }
            }

            @Override
            public void onFailure(Call<TVShowSearch> call, Throwable t) {
            }
        });
    }

    private void getTVShows(final TVShowSearch tvShowSearch) {
        for (TVShow tvShow : tvShowSearch.getTvShows()) {
            final Call<TVShow> callTvShow = mTheMovieDBAPI.getTVShow(tvShow.getTvShowId());
            callTvShow.enqueue(new Callback<TVShow>() {
                @Override
                public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                    mView.get().addTVShowList(response.body());
                }

                @Override
                public void onFailure(Call<TVShow> call, Throwable t) {
                }
            });
        }
    }
}
