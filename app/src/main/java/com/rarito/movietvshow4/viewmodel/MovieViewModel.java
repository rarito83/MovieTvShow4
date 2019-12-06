package com.rarito.movietvshow4.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rarito.movietvshow4.BuildConfig;
import com.rarito.movietvshow4.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    String API_KEY = BuildConfig.TMDB_API_KEY;
    String URL = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";

    public void setListMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listMov = new ArrayList<>();

        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject movObj = new JSONObject(result);
                    JSONArray movArray = movObj.getJSONArray("results");

                    for (int i = 0; i < movArray.length(); i++) {
                        JSONObject movie = movArray.getJSONObject(i);
                        Movie mov = new Movie(movie);
                        listMov.add(mov);
                    }
                    listMovies.postValue(listMov);

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

    public LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }
}
