package com.rarito.movietvshow4.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rarito.movietvshow4.BuildConfig;
import com.rarito.movietvshow4.model.TvShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TvShow>> listTvShows = new MutableLiveData<>();
    String API_KEY = BuildConfig.TMDB_API_KEY;
    String URL = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";

    public void setListTvShows() {
        AsyncHttpClient tvClient = new AsyncHttpClient();
        final ArrayList<TvShow> listTv = new ArrayList<>();

        tvClient.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject tvObject = new JSONObject(result);
                    JSONArray tvArray = tvObject.getJSONArray("results");

                    for (int t = 0; t < tvArray.length(); t++) {
                        JSONObject tv = tvArray.getJSONObject(t);
                        TvShow tvShow = new TvShow(tv);
                        listTv.add(tvShow);
                    }
                    listTvShows.postValue(listTv);
                } catch (JSONException e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("On Failure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getTvShows() {
        return listTvShows;
    }
}
