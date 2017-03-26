package com.hexan.tvwatchlist.presenter.following;

import com.hexan.tvwatchlist.model.TVShow;

import java.util.List;

/**
 * Created by james_000 on 3/15/2017.
 */

public interface FollowingContract {
    interface Presenter {
        void loadFollowingTVShows();
    }
    interface View {
        void setTVShowList(List<TVShow> tvShows);
        void addTVShowList(TVShow tvShow);
    }
}
