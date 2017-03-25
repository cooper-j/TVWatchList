package com.hexan.tvwatchlist.presenter;

import android.support.annotation.NonNull;

import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.model.TVShowSearch;
import com.hexan.tvwatchlist.model.TVShow_Table;
import com.hexan.tvwatchlist.network.TheMovieDBAPI;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
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
                .where(TVShow_Table.isFollowing.eq(true))
                .and(TVShow_Table.status.notEq("Ended"))
                .async()
                .queryListResultCallback(new QueryTransaction.QueryResultListCallback<TVShow>() {
                    @Override
                    public void onListQueryResult(QueryTransaction transaction, @NonNull List<TVShow> tResult) {
                        if (mView != null)
                            mView.get().setTVShowList(tResult);
                    }
                }).execute();
    }
}
