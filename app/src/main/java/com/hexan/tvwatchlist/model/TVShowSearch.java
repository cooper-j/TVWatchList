package com.hexan.tvwatchlist.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by james_000 on 3/16/2017.
 */

public class TVShowSearch {
        @SerializedName("results")
        private List<TVShow> tvShows;

        public List<TVShow> getTvShows() {
                return tvShows;
        }

        public void setTvShows(List<TVShow> tvShows) {
                this.tvShows = tvShows;
        }
}
