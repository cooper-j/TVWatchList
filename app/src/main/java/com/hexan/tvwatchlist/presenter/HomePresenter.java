package com.hexan.tvwatchlist.presenter;

import java.lang.ref.WeakReference;

/**
 * Created by james_000 on 3/15/2017.
 */

public class HomePresenter implements HomeContract.Presenter {
    private WeakReference<HomeContract.View> mView;

    public HomePresenter(HomeContract.View view) {
        mView = new WeakReference<>(view);
    }

}
