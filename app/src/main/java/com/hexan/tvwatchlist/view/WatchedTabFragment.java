package com.hexan.tvwatchlist.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexan.tvwatchlist.R;

import butterknife.ButterKnife;

/**
 * Created by james_000 on 3/15/2017.
 */
public class WatchedTabFragment extends Fragment {
    public WatchedTabFragment() {
    }

    public static WatchedTabFragment newInstance() {
        WatchedTabFragment fragment = new WatchedTabFragment();
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
        View view = inflater.inflate(R.layout.fragment_watched_tab, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
