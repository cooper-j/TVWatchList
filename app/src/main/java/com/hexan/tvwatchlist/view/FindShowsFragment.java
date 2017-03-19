package com.hexan.tvwatchlist.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.adapter.TVShowSearchAdapter;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.presenter.FindShowsContract;
import com.hexan.tvwatchlist.presenter.FindShowsPresenter;
import com.hexan.tvwatchlist.presenter.MainContract;
import com.hexan.tvwatchlist.presenter.OnTVShowClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindShowsFragment extends Fragment implements FindShowsContract.View {
    public static final String TAG = "FindShowsFragment";

    @BindView(R.id.root_content)
    ViewGroup rootContent;
    @BindView(R.id.show_list)
    RecyclerView showRecyclerView;
    @BindView(R.id.search_txt)
    TextView searchTextView;
    @BindView(R.id.no_results_txt)
    TextView noResultsTextView;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.retry_search)
    Button retrySearchButton;
    @BindView(R.id.loading_data)
    ProgressBar loadingDataProgress;

    private FindShowsPresenter mPresenter;
    private MainContract mMainListener;
    private OnTVShowClickListener mListener;

    public FindShowsFragment() {
    }

    public static FindShowsFragment newInstance() {
        FindShowsFragment fragment = new FindShowsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainContract) {
            mMainListener = (MainContract) context;
            mMainListener.onUpdateTile(getString(R.string.title_find_shows));
        } else
            throw new RuntimeException(context.toString() + " must implement MainContract");

        if (context instanceof OnTVShowClickListener)
            mListener = (OnTVShowClickListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainListener = null;
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_shows, container, false);
        ButterKnife.bind(this, view);

        showRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        showRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));

        mPresenter = new FindShowsPresenter(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified())
                    searchView.setIconified(true);
                //myActionMenuItem.collapseActionView();
                loadingDataProgress.setVisibility(View.VISIBLE);
                noResultsTextView.setVisibility(View.INVISIBLE);
                searchTextView.setVisibility(View.INVISIBLE);
                showRecyclerView.setVisibility(View.INVISIBLE);
                mPresenter.searchTVShows(query);
                searchView.setQuery(query, false);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void setTVShowList(List<TVShow> tvShows) {
        retrySearchButton.setClickable(true);
        errorLayout.setVisibility(View.INVISIBLE);
        loadingDataProgress.setVisibility(View.INVISIBLE);

        if (tvShows.isEmpty()) {
            noResultsTextView.setVisibility(View.VISIBLE);
            showRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            showRecyclerView.setVisibility(View.VISIBLE);
            showRecyclerView.setAdapter(new TVShowSearchAdapter(getContext(), tvShows, mListener));
        }
    }

    @Override
    public void onError() {
        retrySearchButton.setClickable(true);
        errorLayout.setVisibility(View.VISIBLE);
        loadingDataProgress.setVisibility(View.INVISIBLE);
        noResultsTextView.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.retry_search)
    void onRetryClick() {
        retrySearchButton.setClickable(false);
        mPresenter.retryLastSearch();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mMainListener != null)
            mMainListener.onUpdateTile(getString(R.string.title_find_shows));
    }
}
