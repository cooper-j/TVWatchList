package com.hexan.tvwatchlist.dagger.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hexan.tvwatchlist.network.TheMovieDBAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by james_000 on 3/17/2017.
 */

@Module
public class NetModule {
    String mBaseUrl;

    public NetModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    public TheMovieDBAPI provideTheMovieDBAPI() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mBaseUrl)
                .build()
                .create(TheMovieDBAPI.class);
    }
}