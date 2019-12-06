package com.rarito.movietvshow4.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rarito.movietvshow4.R;
import com.rarito.movietvshow4.activity.detail.DetailTvShowActivity;
import com.rarito.movietvshow4.adapter.TvShowAdapter;
import com.rarito.movietvshow4.model.TvShow;
import com.rarito.movietvshow4.viewmodel.TvShowViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements TvShowAdapter.OnItemClickListener {

    private RecyclerView rvTvShows;
    private TvShowAdapter tvShowAdapter;
    private TvShowViewModel tvShowViewModel;
    private ProgressBar progressBar;
    private static String EXTRA_TVSHOW = "extra_tvShow";

    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar_tvshow);

        rvTvShows = view.findViewById(R.id.rv_tvshow);
        rvTvShows.setHasFixedSize(true);
        showTvRecycler();

        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        tvShowViewModel.getTvShows().observe(getViewLifecycleOwner(), getTvShows);
        tvShowViewModel.setListTvShows();

    }

    private Observer<ArrayList<TvShow>> getTvShows = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if (tvShows != null) {
                tvShowAdapter.setTvShowsData(tvShows);
                showLoading(false);
            }
        }

        private void showLoading(Boolean b) {
            if (b) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    public void showTvRecycler() {
        rvTvShows.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvShowAdapter = new TvShowAdapter(getContext());
        tvShowAdapter.notifyDataSetChanged();
        rvTvShows.setAdapter(tvShowAdapter);

        tvShowAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onTvItemClick(int position) {
        TvShow tvShow = tvShowAdapter.getTvShowsData().get(position);
        Intent tvIntent = new Intent(getActivity(), DetailTvShowActivity.class);
        tvIntent.putExtra(DetailTvShowActivity.EXTRA_TVSHOW, tvShow);
        startActivity(tvIntent);
        Log.d("Click", String.valueOf(position));
    }
}
