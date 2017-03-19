package com.hexan.tvwatchlist;

import android.app.Application;

import com.hexan.tvwatchlist.dagger.component.DaggerNetComponent;
import com.hexan.tvwatchlist.dagger.component.NetComponent;
import com.hexan.tvwatchlist.dagger.module.AppModule;
import com.hexan.tvwatchlist.dagger.module.NetModule;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by james_000 on 3/15/2017.
 */

public class App extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(new FlowConfig.Builder(this).build());

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.themoviedb.org/3/"))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
