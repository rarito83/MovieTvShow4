package com.rarito.movietvshow4.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie_tvShow_favorite";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE_FAV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s DOUBLE NOT NULL," +
                    " %s DOUBLE NOT NULL," +
                    " %s TEXT NOT NULL)",
            DbContract.TABLE_MOVIE,
            DbContract.MovieFavoriteList._ID,
            DbContract.MovieFavoriteList.TITLE,
            DbContract.MovieFavoriteList.RELEASE,
            DbContract.MovieFavoriteList.LANGUAGE,
            DbContract.MovieFavoriteList.POPULARITY,
            DbContract.MovieFavoriteList.VOTE,
            DbContract.MovieFavoriteList.POSTER
    );

    private static final String SQL_CREATE_TABLE_TVSHOW_FAV = String.format("CREATE TABLE %s"
                    + " (%s INTEGER  PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s DOUBLE NOT NULL," +
                    " %s DOUBLE NOT NULL," +
                    " %s TEXT NOT NULL)",
            DbContract.TABLE_TVSHOW,
            DbContract.TvShowFavoriteList._ID,
            DbContract.TvShowFavoriteList.TITLE_TVSHOW,
            DbContract.TvShowFavoriteList.RELEASE_TVSHOW,
            DbContract.TvShowFavoriteList.LANGUAGE_TVSHOW,
            DbContract.TvShowFavoriteList.POPULARITY_TVSHOW,
            DbContract.TvShowFavoriteList.VOTE_TVSHOW,
            DbContract.TvShowFavoriteList.POSTER_TVSHOW
    );


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE_FAV);
        db.execSQL(SQL_CREATE_TABLE_TVSHOW_FAV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_TVSHOW);
        onCreate(db);
    }
}
