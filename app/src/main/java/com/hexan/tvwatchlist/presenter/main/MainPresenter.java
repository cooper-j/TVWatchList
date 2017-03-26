package com.hexan.tvwatchlist.presenter.main;

import com.hexan.tvwatchlist.model.Genre;
import com.hexan.tvwatchlist.network.TheMovieDBAPI;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by james_000 on 3/15/2017.
 */

public class MainPresenter implements MainContract.Presenter {
    private WeakReference<MainContract.View> mView;
    final TheMovieDBAPI mTheMovieDBAPI;

    public MainPresenter(MainContract.View view) {
        mView = new WeakReference<>(view);
        mTheMovieDBAPI = TheMovieDBAPI.retrofit.create(TheMovieDBAPI.class);
    }
}
