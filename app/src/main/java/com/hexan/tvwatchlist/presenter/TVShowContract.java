package com.hexan.tvwatchlist.presenter;

import com.hexan.tvwatchlist.model.TVShow;

/**
 * Created by james_000 on 3/15/2017.
 */

public interface TVShowContract {
    interface Presenter {
        void getTVShowData(int tvShowId);
    }
    interface View {
        void loadTVShow(TVShow body);
        void onError();
    }
}
