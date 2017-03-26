package com.hexan.tvwatchlist.network;

import com.hexan.tvwatchlist.model.Genre;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.model.TVShowSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by james_000 on 3/16/2017.
 */

public interface TheMovieDBAPI {

    @GET("genre/movie/list?api_key=da51643803b9f9a89c40c2d0d210d7d1")
    Call<GenresResponse> getGenres();

    @GET("search/tv?api_key=da51643803b9f9a89c40c2d0d210d7d1")
    Call<TVShowSearch> searchTVShows(
            @Query("query") String query,
            @Query("page") int page);

    @GET("tv/{tv_id}?api_key=da51643803b9f9a89c40c2d0d210d7d1")
    Call<TVShow> getTVShow(
            @Path("tv_id") int id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
