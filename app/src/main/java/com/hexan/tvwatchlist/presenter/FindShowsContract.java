package com.hexan.tvwatchlist.presenter;

import com.hexan.tvwatchlist.model.TVShow;

import java.util.List;

/**
 * Created by james_000 on 3/15/2017.
 */

public interface FindShowsContract {
    interface Presenter {
        void searchTVShows(String searchString);
        void retryLastSearch();
    }
    interface View {
        void setTVShowList(List<TVShow> tvShows);
        void onError();
    }
}
