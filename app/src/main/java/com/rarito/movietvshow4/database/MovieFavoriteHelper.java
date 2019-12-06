package com.rarito.movietvshow4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rarito.movietvshow4.model.MovieFavorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.rarito.movietvshow4.database.DbContract.MovieFavoriteList.LANGUAGE;
import static com.rarito.movietvshow4.database.DbContract.MovieFavoriteList.POPULARITY;
import static com.rarito.movietvshow4.database.DbContract.MovieFavoriteList.POSTER;
import static com.rarito.movietvshow4.database.DbContract.MovieFavoriteList.RELEASE;
import static com.rarito.movietvshow4.database.DbContract.MovieFavoriteList.TITLE;
import static com.rarito.movietvshow4.database.DbContract.MovieFavoriteList.VOTE;
import static com.rarito.movietvshow4.database.DbContract.TABLE_MOVIE;

public class MovieFavoriteHelper {

    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DbHelper dbHelper;
    private static MovieFavoriteHelper INSTANCE;
    private static SQLiteDatabase sqLiteDatabase;

    private MovieFavoriteHelper(Context context) {
        dbHelper = new DbHelper(context);
    }

    public static MovieFavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieFavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

        if (sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }

    public Cursor queryAll() {
        return sqLiteDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
        );
    }

    public ArrayList<MovieFavorite> getAllMoviesFav() {
        ArrayList<MovieFavorite> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");

        cursor.moveToFirst();
        MovieFavorite movieFavorite;
        if (cursor.getCount() > 0) {
            do {
                movieFavorite = new MovieFavorite();
                movieFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieFavorite.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));
                movieFavorite.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));
                movieFavorite.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(POPULARITY)));
                movieFavorite.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE)));
                movieFavorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));

                arrayList.add(movieFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(MovieFavorite movieFavorite) {

        ContentValues args = new ContentValues();
        args.put(TITLE, movieFavorite.getTitle());
        args.put(RELEASE, movieFavorite.getReleaseDate());
        args.put(LANGUAGE, movieFavorite.getLanguage());
        args.put(POPULARITY, (Double) movieFavorite.getPopularity());
        args.put(VOTE, (Double) movieFavorite.getVoteAverage());
        args.put(POSTER, movieFavorite.getPoster());
        Log.d("INSERT MOVIE", args.toString());
        return sqLiteDatabase.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id) {
        return sqLiteDatabase.delete(TABLE_MOVIE, _ID + "='" + id + "'", null);
    }
}
