package com.rarito.movietvshow4.database;

import android.provider.BaseColumns;

public class DbContract {

    static String TABLE_MOVIE = "movie_favorite";

    public static final class MovieFavoriteList implements BaseColumns {


        static String TITLE = "movie_title";
        static String RELEASE = "movie_release";
        static String LANGUAGE = "movie_language";
        static String POPULARITY = "movie_popularity";
        static String VOTE = "movie_vote";
        static String POSTER = "movie_poster";
    }

    static String TABLE_TVSHOW = "tvShow_favorite";

    public static final class TvShowFavoriteList implements BaseColumns {

        static String TITLE_TVSHOW = "tvShow_title";
        static String RELEASE_TVSHOW = "tvShow_release";
        static String LANGUAGE_TVSHOW = "tvShow_language";
        static String POPULARITY_TVSHOW = "tvShow_popularity";
        static String VOTE_TVSHOW = "tvShow_vote";
        static String POSTER_TVSHOW = "tvShow_poster";
    }
}