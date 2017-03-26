package com.hexan.tvwatchlist.presenter.tvshow;

import com.hexan.tvwatchlist.model.Genre;
import com.hexan.tvwatchlist.model.TVShow;

import java.util.List;

/**
 * Created by james_000 on 3/15/2017.
 */

public interface TVShowContract {
    interface Presenter {
        void loadTVShowData(int tvShowId);
        void updateTVShow(TVShow mTVshow, boolean isFollowed);
    }
    interface View {
        void displayTVShow(TVShow body);
        void displayTVShowGenres(String genres);
        void setIsFollowing(boolean isFollowing);
        void onError();
    }
}
