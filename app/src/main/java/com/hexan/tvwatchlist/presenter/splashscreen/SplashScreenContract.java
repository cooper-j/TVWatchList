package com.hexan.tvwatchlist.presenter.splashscreen;

/**
 * Created by james_000 on 3/19/2017.
 */

public interface SplashScreenContract {
    void onUpdateTile(String title);
    void onUpdateTileSubtitle(String title, String subtitle);

    interface View {
        void onUpdateCompleted();
        void onError();
    }

    interface Presenter {
        void updateGenres();
    }
}
