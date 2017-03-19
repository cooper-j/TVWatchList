package com.hexan.tvwatchlist.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.presenter.OnTVShowClickListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnTVShowClickListener {

    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*TVShow tv = new TVShow();
        tv.setTvShowId(0);
        tv.setName("Game of thrones");
        tv.setFollowing(true);
        tv.save();
        tv = new TVShow();
        tv.setTvShowId(1);
        tv.setName("House of cards");
        tv.setFollowing(true);
        tv.save();
        tv = new TVShow();
        tv.setTvShowId(2);
        tv.setName("Skies");
        tv.setFollowing(true);
        tv.save();
        tv = new TVShow();
        tv.setTvShowId(3);
        tv.setName("The peaky blinders");
        tv.setFollowing(true);
        tv.save();*/

        replaceFragment(HomeFragment.newInstance(), HomeFragment.TAG);
    }

    private void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().popBackStackImmediate();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1)
                finish();
            else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_my_shows:
                replaceFragment(HomeFragment.newInstance(), HomeFragment.TAG);
                break;
            case R.id.nav_find_shows:
                replaceFragment(FindShowsFragment.newInstance(), FindShowsFragment.TAG);
                break;
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();
        return true;
    }

    @Override
    public void onTVShowClick(TVShow tvShow) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content_main, TVShowFragment.newInstance(tvShow), TVShowFragment.TAG)
                .hide(fm.getFragments().listIterator().next())
                .addToBackStack(TVShowFragment.TAG)
                .commit();
    }
}
