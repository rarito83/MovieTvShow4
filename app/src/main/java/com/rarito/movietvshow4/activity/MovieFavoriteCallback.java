package com.rarito.movietvshow4.activity;

import com.rarito.movietvshow4.model.MovieFavorite;

import java.util.ArrayList;

public interface MovieFavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<MovieFavorite> movieFavorites);
}
