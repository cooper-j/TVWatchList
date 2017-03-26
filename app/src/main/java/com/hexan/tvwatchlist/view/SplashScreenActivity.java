package com.hexan.tvwatchlist.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.presenter.splashscreen.SplashScreenContract;
import com.hexan.tvwatchlist.presenter.splashscreen.SplashScreenPresenter;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenContract.View {

    private SplashScreenPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mPresenter = new SplashScreenPresenter(this);
        //mPresenter.updateGenres();
        onUpdateCompleted();
    }

    @Override
    public void onUpdateCompleted() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onError() {

    }
}
