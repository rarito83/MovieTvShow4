package com.rarito.movietvshow4.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rarito.movietvshow4.R;
import com.rarito.movietvshow4.activity.MovieFavoriteCallback;
import com.rarito.movietvshow4.adapter.MovieFavoriteAdapter;
import com.rarito.movietvshow4.database.MovieFavoriteHelper;
import com.rarito.movietvshow4.model.MovieFavorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment implements MovieFavoriteCallback, MovieFavoriteAdapter.OnItemClickListener {

    private MovieFavoriteHelper movieFavoriteHelper;
    private RecyclerView rvMovieFav;
    private ProgressBar progressBar;
    private MovieFavoriteAdapter moviesFavAdapter;
    private MovieFavorite movieFavorite;
    private int position;
    private final int ALERT_DIALOG_CLOSE = 10;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_DELETE = 20;
    private static final String MOVIE_STATE = "movie_state";
    public static final String EXTRA_POSITION = "extra_position";

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View movView = inflater.inflate(R.layout.fragment_movie_favorite, container, false);

        progressBar = movView.findViewById(R.id.progressBar_movie_favorite);
        movieFavoriteHelper = MovieFavoriteHelper.getInstance(getContext());
        movieFavoriteHelper.open();

        rvMovieFav = movView.findViewById(R.id.rv_movie_favorite);
        rvMovieFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovieFav.setHasFixedSize(true);
        moviesFavAdapter = new MovieFavoriteAdapter(getActivity());
        moviesFavAdapter.notifyDataSetChanged();
        rvMovieFav.setAdapter(moviesFavAdapter);
        moviesFavAdapter.setOnItemClickListener(this);

        new LoadMovieAsync(movieFavoriteHelper, this).execute();

        if (savedInstanceState == null) {
            new LoadMovieAsync(movieFavoriteHelper, this).execute();
        } else {
            ArrayList<MovieFavorite> movieFavList = savedInstanceState.getParcelableArrayList(MOVIE_STATE);
            if (movieFavList != null) {
                moviesFavAdapter.setMovieFavorites(movieFavList);
            }

        }
        return movView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == MovieFavoriteFragment.RESULT_DELETE) {
            int pos = data.getIntExtra(MovieFavoriteFragment.EXTRA_POSITION, 0);
            moviesFavAdapter.removeItem(pos);
            showSnackbarMessage(getString(R.string.notify_delete_movie));
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovieFav, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_POSITION, moviesFavAdapter.getMovieFavorites());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        movieFavoriteHelper.close();
    }


    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<MovieFavorite> movieFavorites) {
        progressBar.setVisibility(View.INVISIBLE);
        if (movieFavorites.size() > 0) {
            moviesFavAdapter.setMovieFavorites(movieFavorites);
        } else {
            moviesFavAdapter.setMovieFavorites(new ArrayList<MovieFavorite>());
        }
    }

    @Override
    public void onMovFavItemClick(int position) {
        showAlertDialog(ALERT_DIALOG_DELETE, position);
    }

    private void showAlertDialog(int type, final int idMovFav) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogMessage = getString(R.string.notify_question_delete);
            dialogTitle = getString(R.string.notify_delete_movie);

        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (isDialogClose) {
                            getActivity().finish();
                        } else {
                            long result = movieFavoriteHelper.deleteMovie(idMovFav);
                            if (result > 0) {
                                moviesFavAdapter.removeItem(position);
                            } else {
                                Toast.makeText(getActivity(), getString(R.string.notify_failed_delete_data), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<MovieFavorite>> {

        private final WeakReference<MovieFavoriteHelper> weakMovieHelper;
        private final WeakReference<MovieFavoriteCallback> weakCallback;

        private LoadMovieAsync(MovieFavoriteHelper movieHelper, MovieFavoriteCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<MovieFavorite> doInBackground(Void... voids) {
            return weakMovieHelper.get().getAllMoviesFav();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieFavorite> movieFavorites) {
            super.onPostExecute(movieFavorites);

            weakCallback.get().postExecute(movieFavorites);
        }
    }
}