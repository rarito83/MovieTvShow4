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
import com.rarito.movietvshow4.activity.detail.DetailMovieActivity;
import com.rarito.movietvshow4.adapter.MovieAdapter;
import com.rarito.movietvshow4.model.Movie;
import com.rarito.movietvshow4.viewmodel.MovieViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieAdapter.OnItemClickListener {

    private RecyclerView rvMovies;
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private ProgressBar prBar;
    //    private ArrayList<Movie> movie = new ArrayList<>();
    private static String EXTRA_MOVIE = "extra_movie";

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prBar = view.findViewById(R.id.progressBar_movie);

        rvMovies = view.findViewById(R.id.rv_movie);
        rvMovies.setHasFixedSize(true);
        showMovRecycler();

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(getViewLifecycleOwner(), getMovies);
        movieViewModel.setListMovies();

    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                movieAdapter.setMoviesData(movies);
                showLoad(false);
            }
        }

        private void showLoad(boolean b) {
            if (b) {
                prBar.setVisibility(View.VISIBLE);
            } else {
                prBar.setVisibility(View.GONE);
            }
        }
    };

    private void showMovRecycler() {
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();
        rvMovies.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onMovieItemClick(int position) {
        Movie movies = movieAdapter.getMoviesData().get(position);

        Intent movIntent = new Intent(getActivity(), DetailMovieActivity.class);
        movIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movies);
        startActivity(movIntent);
        Log.d("Click", String.valueOf(position));
    }
}
