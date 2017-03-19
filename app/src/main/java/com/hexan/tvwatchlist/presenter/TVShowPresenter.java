package com.hexan.tvwatchlist.presenter;

import android.support.annotation.Nullable;

import com.hexan.tvwatchlist.AppDatabase;
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
    public void getTVShowData(final int tvShowId) {
        SQLite.select()
                .from(TVShow.class)
                .where(TVShow_Table.tvShowId.eq(tvShowId))
                .async()
                .querySingleResultCallback(new QueryTransaction.QueryResultSingleCallback<TVShow>() {
                    @Override
                    public void onSingleQueryResult(QueryTransaction transaction, @Nullable TVShow tvShow) {
                        if (tvShow != null) {
                            if (mView.get() != null)
                                mView.get().setIsFollowing(tvShow.isFollowing());
                            requestTVShowData(tvShow);
                        } else
                            requestTVShowData(tvShowId);
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

    private void requestTVShowData(final TVShow tvShow) {
        final Call<TVShow> callTvShow = mTheMovieDBAPI.getTVShow(tvShow.getTvShowId());
        callTvShow.enqueue(new Callback<TVShow>() {
            @Override
            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                if (mView.get() != null) {
                    if (response.isSuccessful()) {
                        TVShow responseShow = response.body();
                        responseShow.setFollowing(tvShow.isFollowing());
                        ProcessModelTransaction<TVShow> processModelTransaction =
                                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<TVShow>() {
                                    @Override
                                    public void processModel(TVShow model, DatabaseWrapper wrapper) {
                                        for (Season season : model.getSeasons()) {
                                            season.setTvShow(model);
                                            season.save();
                                        }
                                        model.update();
                                    }
                                }).add(responseShow).build();
                        DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                        database.beginTransactionAsync(processModelTransaction)
                                .build().execute();
                        mView.get().loadTVShow(responseShow);
                    } else
                        mView.get().loadTVShow(tvShow);
                }
            }

            @Override
            public void onFailure(Call<TVShow> call, Throwable t) {
                if (mView.get() != null)
                    mView.get().loadTVShow(tvShow);
            }
        });
    }

    private void requestTVShowData(final int tvShowId) {
        final Call<TVShow> callTvShow = mTheMovieDBAPI.getTVShow(tvShowId);
        callTvShow.enqueue(new Callback<TVShow>() {
            @Override
            public void onResponse(Call<TVShow> call, Response<TVShow> response) {
                if (mView.get() != null) {
                    if (response.isSuccessful())
                        mView.get().loadTVShow(response.body());
                    else
                        mView.get().onError();
                }
            }

            @Override
            public void onFailure(Call<TVShow> call, Throwable t) {
                if (mView.get() != null)
                    mView.get().onError();
            }
        });
    }
}
