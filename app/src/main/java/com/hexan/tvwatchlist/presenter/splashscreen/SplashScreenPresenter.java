package com.hexan.tvwatchlist.presenter.splashscreen;

import com.hexan.tvwatchlist.AppDatabase;
import com.hexan.tvwatchlist.model.Genre;
import com.hexan.tvwatchlist.model.Season;
import com.hexan.tvwatchlist.model.TVShow;
import com.hexan.tvwatchlist.network.GenresResponse;
import com.hexan.tvwatchlist.network.TheMovieDBAPI;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by james_000 on 3/15/2017.
 */

public class SplashScreenPresenter implements SplashScreenContract.Presenter {
    private WeakReference<SplashScreenContract.View> mView;
    final TheMovieDBAPI mTheMovieDBAPI;

    public SplashScreenPresenter(SplashScreenContract.View view) {
        mView = new WeakReference<>(view);
        mTheMovieDBAPI = TheMovieDBAPI.retrofit.create(TheMovieDBAPI.class);
    }

    @Override
    public void updateGenres() {
        final Call<GenresResponse> call = mTheMovieDBAPI.getGenres();

        call.enqueue(new Callback<GenresResponse>() {
            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {
                if (response.isSuccessful() && response.body().getGenres() != null && mView != null) {
                    ProcessModelTransaction<Genre> processModelTransaction =
                            new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<Genre>() {
                                @Override
                                public void processModel(Genre model, DatabaseWrapper wrapper) {
                                    model.save();
                                }
                            }).addAll(response.body().getGenres()).build();
                    DatabaseDefinition database = FlowManager.getDatabase(AppDatabase.class);
                    database.beginTransactionAsync(processModelTransaction)
                            .success(new Transaction.Success() {
                                @Override
                                public void onSuccess(Transaction transaction) {
                                    mView.get().onUpdateCompleted();
                                }
                            }).error(new Transaction.Error() {
                        @Override
                        public void onError(Transaction transaction, Throwable error) {
                            mView.get().onUpdateCompleted();
                        }
                    })
                            .build().execute();
                } else if (mView != null)
                    mView.get().onUpdateCompleted();
            }

            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {
                if (mView != null)
                    mView.get().onUpdateCompleted();
            }
        });
    }
}
