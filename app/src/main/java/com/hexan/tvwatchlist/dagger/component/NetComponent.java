package com.hexan.tvwatchlist.dagger.component;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.hexan.tvwatchlist.dagger.module.AppModule;
import com.hexan.tvwatchlist.dagger.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by james_000 on 3/17/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(Application app);
    void inject(Activity activity);
    void inject(Fragment fragment);
}