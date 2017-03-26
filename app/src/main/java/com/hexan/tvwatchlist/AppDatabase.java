package com.hexan.tvwatchlist;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by james_000 on 3/15/2017.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "TVWatchList2";

    public static final int VERSION = 2;
}