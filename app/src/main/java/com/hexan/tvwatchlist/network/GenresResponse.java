package com.hexan.tvwatchlist.network;

import com.hexan.tvwatchlist.model.Genre;

import java.util.List;

/**
 * Created by james_000 on 3/25/2017.
 */

public class GenresResponse {

    private List<Genre> genres;

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
