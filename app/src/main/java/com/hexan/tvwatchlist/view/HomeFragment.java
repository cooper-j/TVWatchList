package com.hexan.tvwatchlist.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.adapter.HomePagerAdapter;
import com.hexan.tvwatchlist.presenter.HomeContract;
import com.hexan.tvwatchlist.presenter.HomePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeContract.View {
    public static final String TAG = "HomeFragment";

    @BindView(R.id.home_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.home_view_pager)
    ViewPager viewPager;

    private HomePresenter mPresenter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new HomePresenter(this);

        viewPager.setAdapter(new HomePagerAdapter(getContext(), getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
