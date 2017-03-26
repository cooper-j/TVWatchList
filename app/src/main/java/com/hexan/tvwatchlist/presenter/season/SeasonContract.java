package com.hexan.tvwatchlist.presenter.season;

import com.hexan.tvwatchlist.model.Season;
import com.hexan.tvwatchlist.model.TVShow;

/**
 * Created by james_000 on 3/15/2017.
 */

public interface SeasonContract {
    interface Presenter {
        void loadSeasonData(Season seasonId);
    }
    interface View {
        void displaySeason(Season season);
        void onError();
    }
}
