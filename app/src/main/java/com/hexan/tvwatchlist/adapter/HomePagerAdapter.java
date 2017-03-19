package com.hexan.tvwatchlist.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.view.FollowingTabFragment;
import com.hexan.tvwatchlist.view.WatchedTabFragment;

import java.lang.ref.WeakReference;

/**
 * Created by james_000 on 3/15/2017.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private WeakReference<Context> mContext;

    public HomePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = new WeakReference<Context>(context);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FollowingTabFragment();
            case 1:
            default:
                return new WatchedTabFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mContext.get() != null) {
            switch (position) {
                case 0:
                default:
                    return mContext.get().getString(R.string.following);
                case 1:
                    return mContext.get().getString(R.string.watched);
            }
        }
        else return "";
    }
}
