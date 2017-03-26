package com.hexan.tvwatchlist.presenter.findshow;

import com.hexan.tvwatchlist.model.TVShowSearch;
import com.hexan.tvwatchlist.network.TheMovieDBAPI;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by james_000 on 3/15/2017.
 */

public class FindShowsPresenter implements FindShowsContract.Presenter {
    private WeakReference<FindShowsContract.View> mView;
    final TheMovieDBAPI mTheMovieDBAPI;
    String lastQuery;

    public FindShowsPresenter(FindShowsContract.View view) {
        mView = new WeakReference<>(view);
        mTheMovieDBAPI = TheMovieDBAPI.retrofit.create(TheMovieDBAPI.class);
    }

    @Override
    public void searchTVShows(final String searchString) {
        lastQuery = searchString;
        final Call<TVShowSearch> call = mTheMovieDBAPI.searchTVShows(searchString, 1);

        call.enqueue(new Callback<TVShowSearch>() {
            @Override
            public void onResponse(Call<TVShowSearch> call, Response<TVShowSearch> response) {
                if (response.isSuccessful() && mView != null) {
                    mView.get().setTVShowList(response.body().getTvShows());
                } else
                    mView.get().onError();
            }

            @Override
            public void onFailure(Call<TVShowSearch> call, Throwable t) {
                if (mView != null)
                    mView.get().onError();
            }
        });
    }

    @Override
    public void retryLastSearch() {
        if (lastQuery != null && !lastQuery.isEmpty())
            searchTVShows(lastQuery);
        else
            mView.get().onError();
    }
}
