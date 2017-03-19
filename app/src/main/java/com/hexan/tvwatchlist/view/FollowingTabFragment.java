package com.hexan.tvwatchlist.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hexan.tvwatchlist.R;
import com.hexan.tvwatchlist.adapter.TVShowAdapter;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.presenter.FollowingContract;
import com.hexan.tvwatchlist.presenter.FollowingPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by james_000 on 3/15/2017.
 */
public class FollowingTabFragment extends Fragment implements FollowingContract.View {

    @BindView(R.id.following_grid_view)
    RecyclerView followingGridView;

    private FollowingPresenter mPresenter;

    public FollowingTabFragment() {
    }

    public static FollowingTabFragment newInstance() {
        FollowingTabFragment fragment = new FollowingTabFragment();
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
        View view = inflater.inflate(R.layout.fragment_following_tab, container, false);
        ButterKnife.bind(this, view);

        followingGridView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        followingGridView.addItemDecoration(new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.grid_seperator_size), 4));

        mPresenter = new FollowingPresenter(this);
        mPresenter.loadFollowingTVShows();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void setTVShowList(List<TVShow> tvShows) {
        followingGridView.setAdapter(new TVShowAdapter(getContext(), tvShows));
    }

    @Override
    public void addTVShowList(TVShow tvShow) {
        TVShowAdapter adapter = (TVShowAdapter)followingGridView.getAdapter();
        if (adapter == null) {
            adapter = new TVShowAdapter(getContext(), new ArrayList<TVShow>());
            followingGridView.setAdapter(adapter);
        }
        adapter.addItem(tvShow);
    }

    public class ItemDecorationAlbumColumns extends RecyclerView.ItemDecoration {

        private int mSizeGridSpacingPx;
        private int mGridSize;

        private boolean mNeedLeftSpacing = false;

        public ItemDecorationAlbumColumns(int gridSpacingPx, int gridSize) {
            mSizeGridSpacingPx = gridSpacingPx;
            mGridSize = gridSize;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int frameWidth = (int) ((parent.getWidth() - (float) mSizeGridSpacingPx * (mGridSize - 1)) / mGridSize);
            int padding = parent.getWidth() / mGridSize - frameWidth;
            int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
            if (itemPosition < mGridSize) {
                outRect.top = 0;
            } else {
                outRect.top = mSizeGridSpacingPx;
            }
            if (itemPosition % mGridSize == 0) {
                outRect.left = 0;
                outRect.right = padding;
                mNeedLeftSpacing = true;
            } else if ((itemPosition + 1) % mGridSize == 0) {
                mNeedLeftSpacing = false;
                outRect.right = 0;
                outRect.left = padding;
            } else if (mNeedLeftSpacing) {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx - padding;
                if ((itemPosition + 2) % mGridSize == 0) {
                    outRect.right = mSizeGridSpacingPx - padding;
                } else {
                    outRect.right = mSizeGridSpacingPx / 2;
                }
            } else if ((itemPosition + 2) % mGridSize == 0) {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx / 2;
                outRect.right = mSizeGridSpacingPx - padding;
            } else {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx / 2;
                outRect.right = mSizeGridSpacingPx / 2;
            }
            outRect.bottom = 0;
        }
    }
}
