package com.hexan.tvwatchlist.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by james_000 on 3/17/2017.
 */

@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }
}