package com.rarito.movietvshow4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rarito.movietvshow4.model.TvShowFavorite;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.rarito.movietvshow4.database.DbContract.TABLE_TVSHOW;
import static com.rarito.movietvshow4.database.DbContract.TvShowFavoriteList.LANGUAGE_TVSHOW;
import static com.rarito.movietvshow4.database.DbContract.TvShowFavoriteList.POPULARITY_TVSHOW;
import static com.rarito.movietvshow4.database.DbContract.TvShowFavoriteList.POSTER_TVSHOW;
import static com.rarito.movietvshow4.database.DbContract.TvShowFavoriteList.RELEASE_TVSHOW;
import static com.rarito.movietvshow4.database.DbContract.TvShowFavoriteList.TITLE_TVSHOW;
import static com.rarito.movietvshow4.database.DbContract.TvShowFavoriteList.VOTE_TVSHOW;

public class TvShowFavoriteHelper {

    private static final String DATABASE_TABLE = TABLE_TVSHOW;
    private static DbHelper dbHelper;
    private static TvShowFavoriteHelper INSTANCE;
    private static SQLiteDatabase sqLiteDatabase;

    private TvShowFavoriteHelper(Context context) {
        dbHelper = new DbHelper(context);
    }

    public static TvShowFavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowFavoriteHelper(context);
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

    public ArrayList<TvShowFavorite> getAllTvFav() {
        ArrayList<TvShowFavorite> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShowFavorite tvShowFavorite;
        if (cursor.getCount() > 0) {
            do {
                tvShowFavorite = new TvShowFavorite();
                tvShowFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShowFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_TVSHOW)));
                tvShowFavorite.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_TVSHOW)));
                tvShowFavorite.setLanguage(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE_TVSHOW)));
                tvShowFavorite.setPopularity(cursor.getDouble(cursor.getColumnIndexOrThrow(POPULARITY_TVSHOW)));
                tvShowFavorite.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(VOTE_TVSHOW)));
                tvShowFavorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_TVSHOW)));

                arrayList.add(tvShowFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTvShow(TvShowFavorite tvShowFavorite) {
        ContentValues args = new ContentValues();
        args.put(TITLE_TVSHOW, tvShowFavorite.getTitle());
        args.put(RELEASE_TVSHOW, tvShowFavorite.getReleaseDate());
        args.put(LANGUAGE_TVSHOW, tvShowFavorite.getLanguage());
        args.put(POPULARITY_TVSHOW, (Double) tvShowFavorite.getPopularity());
        args.put(VOTE_TVSHOW, (Double) tvShowFavorite.getVoteAverage());
        args.put(POSTER_TVSHOW, tvShowFavorite.getPoster());
        Log.d("INSERT TV SHOW", args.toString());
        return sqLiteDatabase.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTvShow(int id) {
        return sqLiteDatabase.delete(TABLE_TVSHOW, _ID + "='" + id + "'", null);
    }


}
