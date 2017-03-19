package com.hexan.tvwatchlist.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.model.TVShow;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TVShow tv = new TVShow();
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
        tv.save();

        replaceFragment(HomeFragment.newInstance(), HomeFragment.TAG);
    }

    private void replaceFragment(Fragment fragment, String tag) {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0 || !getSupportFragmentManager().getBackStackEntryAt(0).getName().equals(tag))
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, fragment, tag)
                    .addToBackStack(tag)
                    .commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1)
                finish();
            else
                super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        }

        switch (id) {
            case R.id.nav_camera:
                replaceFragment(HomeFragment.newInstance(), HomeFragment.TAG);
                break;
            case R.id.nav_find_shows:
                replaceFragment(FindShowsFragment.newInstance(), FindShowsFragment.TAG);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
