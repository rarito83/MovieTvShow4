package com.rarito.movietvshow4.activity;

import com.rarito.movietvshow4.model.TvShowFavorite;

import java.util.ArrayList;

public interface TvShowFavoriteCallback {
    void preExecute();
    void postExecute(ArrayList<TvShowFavorite> tvShowFavorites);
}
