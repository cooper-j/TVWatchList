package com.hexan.tvwatchlist.presenter.tvshow;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hexan.tvwatchlist.AppDatabase;
import com.hexan.tvwatchlist.model.Genre;
import com.hexan.tvwatchlist.model.Season;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.model.TVShow_Table;
import com.hexan.tvwatchlist.network.TheMovieDBAPI;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.lang.ref.WeakReference;
import java.util.List;

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
    public void loadTVShowData(final int tvShowId) {
        requestTVShowData(tvShowId);
    }

    private void updateTVShowFollowing(@NonNull final TVShow mTVshow) {
        SQLite.select()
                .from(TVShow.class)
                .where(TVShow_Table.tvShowId.eq(mTVshow.getTvShowId()))
                .async()
                .querySingleResultCallback(new QueryTransaction.QueryResultSingleCallback<TVShow>() {
                    @Override
                    public void onSingleQueryResult(QueryTransaction transaction, @Nullable TVShow tvShow) {
                        if (tvShow != null) {
                            mTVshow.setFollowing(tvShow.isFollowing());
                        }
                        if (mView != null)
                            mView.get().displayTVShow(mTVshow);
                        prepareGenres(mTVshow.getGenres());
                    }
                }).execute();
    }

    @Override
    public void updateTVShow(TVShow mTVshow, boolean isFollowed) {
        mTVshow.setFollowing(isFollowed);
        ProcessModelTransaction<TVShow> processModelTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<TVShow>() {
                    @Override
                    public void processModel(TVShow model, DatabaseWrapper wrapper) {
                        for (Season season : model.getSeasons()) {
                            season.setTvShow(model);
                            if (!season.save())
                                season.update();
                        }
                        if (!model.save())
                            model.update();
                    }
                }).processListener(new ProcessModelTransaction.OnModelProcessListener<TVShow>() {
                    @Override
                    public void onModelProcessed(long current, long total, TVShow modifiedModel) {

                    }
                }).add(mTVshow).build();
        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
        database.beginTransactionAsync(processModelTransaction)
                .build().execute();
    }

    private void requestTVShowData(final int tvShowId) {
        final Call<TVShow> callTvShow = mTheMovieDBAPI.getTVShow(tvShowId);
        callTvShow.enqueue(new Callback<TVShow>() {
            @Override
            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                if (mView != null) {
                    if (response.isSuccessful()) {
                        updateTVShowFollowing(response.body());
                    } else
                        getDbTVShowData(tvShowId);
                }
            }

            @Override
            public void onFailure(Call<TVShow> call, Throwable t) {
                getDbTVShowData(tvShowId);
            }
        });
    }

    private void getDbTVShowData(final int tvShowId) {
        SQLite.select()
                .from(TVShow.class)
                .where(TVShow_Table.tvShowId.eq(tvShowId))
                .async()
                .querySingleResultCallback(new QueryTransaction.QueryResultSingleCallback<TVShow>() {
                    @Override
                    public void onSingleQueryResult(QueryTransaction transaction, @Nullable TVShow tvShow) {
                        if (tvShow != null) {
                            if (mView != null) {
                                mView.get().setIsFollowing(tvShow.isFollowing());
                                mView.get().displayTVShow(tvShow);
                                prepareGenres(tvShow.getGenres());
                            }
                        } else if (mView != null)
                            mView.get().onError();
                    }
                }).execute();
    }

    private void prepareGenres(List<Genre> genres) {
        if (genres == null)
            return;
        StringBuilder builder = new StringBuilder();
        String sep = "";

        for (Genre genre : genres) {
            builder.append(sep);
            builder.append(genre.getName());
            sep = ", ";
        }

        if (mView != null)
            mView.get().displayTVShowGenres(builder.toString());
    }
}
