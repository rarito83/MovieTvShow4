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
import com.rarito.movietvshow4.activity.TvShowFavoriteCallback;
import com.rarito.movietvshow4.adapter.TvShowFavoriteAdapter;
import com.rarito.movietvshow4.database.TvShowFavoriteHelper;
import com.rarito.movietvshow4.model.TvShowFavorite;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavoriteFragment extends Fragment implements TvShowFavoriteCallback, TvShowFavoriteAdapter.OnItemClickListener {

    private TvShowFavoriteHelper tvShowFavoriteHelper;
    private RecyclerView rvTvShowFav;
    private ProgressBar progressBar;
    private TvShowFavoriteAdapter tvShowFavoriteAdapter;
    private TvShowFavorite tvShowFavorite;
    private int position;
    private final int ALERT_DIALOG_CLOSE = 10;
    public static final int RESULT_DELETE = 301;
    private final int ALERT_DIALOG_DELETE = 20;
    private static final String TVSHOW_STATE = "tvshow_state";
    public static final String EXTRA_POSITION = "extra_position";

    public TvShowFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View tvView = inflater.inflate(R.layout.fragment_tv_show_favorite, container, false);

        progressBar = tvView.findViewById(R.id.progressBar_tvShow_favorite);
        tvShowFavoriteHelper = TvShowFavoriteHelper.getInstance(getContext());
        tvShowFavoriteHelper.open();

        rvTvShowFav = tvView.findViewById(R.id.rv_tvShow_favorite);
        rvTvShowFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTvShowFav.setHasFixedSize(true);
        tvShowFavoriteAdapter = new TvShowFavoriteAdapter(getActivity());
        tvShowFavoriteAdapter.notifyDataSetChanged();
        rvTvShowFav.setAdapter(tvShowFavoriteAdapter);
        tvShowFavoriteAdapter.setOnItemClickListener(this);

        new LoadTvShowAsync(tvShowFavoriteHelper, this).execute();

        if (savedInstanceState == null) {
            new LoadTvShowAsync(tvShowFavoriteHelper, this).execute();
        } else {
            ArrayList<TvShowFavorite> tvShowFavList = savedInstanceState.getParcelableArrayList(TVSHOW_STATE);
            if (tvShowFavList != null) {
                tvShowFavoriteAdapter.setTvShowFavorites(tvShowFavList);
            }
        }

        return tvView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == TvShowFavoriteFragment.RESULT_DELETE) {
            int position = data.getIntExtra(TvShowFavoriteFragment.EXTRA_POSITION, 0);
            tvShowFavoriteAdapter.removeItem(position);
            showSnackbarMessage(getString(R.string.notify_delete_tvShow));
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvTvShowFav, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_POSITION, tvShowFavoriteAdapter.getTvShowFavorites());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvShowFavoriteHelper.close();
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
    public void postExecute(ArrayList<TvShowFavorite> tvShowFavorites) {
        progressBar.setVisibility(View.INVISIBLE);
        if (tvShowFavorites.size() > 0) {
            tvShowFavoriteAdapter.setTvShowFavorites(tvShowFavorites);
        } else {
            tvShowFavoriteAdapter.setTvShowFavorites(new ArrayList<TvShowFavorite>());
        }
    }

    @Override
    public void onTvShowFavItemClick(int position) {
        showAlertDialog(ALERT_DIALOG_DELETE, position);
    }

    private void showAlertDialog(int type, final int idTvShowFav) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogMessage = getString(R.string.notify_question_delete);
            dialogTitle = getString(R.string.notify_delete_tvShow);

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
                            long result = tvShowFavoriteHelper.deleteTvShow(idTvShowFav);
                            if (result > 0) {
                                tvShowFavoriteAdapter.removeItem(position);
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

    private static class LoadTvShowAsync extends AsyncTask<Void, Void, ArrayList<TvShowFavorite>> {

        private final WeakReference<TvShowFavoriteHelper> weakTvShowHelper;
        private final WeakReference<TvShowFavoriteCallback> weakCallback;

        private LoadTvShowAsync(TvShowFavoriteHelper tvShowHelper, TvShowFavoriteCallback callback) {
            weakTvShowHelper = new WeakReference<>(tvShowHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShowFavorite> doInBackground(Void... voids) {
            return weakTvShowHelper.get().getAllTvFav();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShowFavorite> tvShowFavorites) {
            super.onPostExecute(tvShowFavorites);

            weakCallback.get().postExecute(tvShowFavorites);
        }
    }
}
