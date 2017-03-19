package com.hexan.tvwatchlist.presenter;

/**
 * Created by james_000 on 3/19/2017.
 */

public interface MainContract {
        void onUpdateTile(String title);
        void onUpdateTileSubtitle(String title, String subtitle);
}
