package com.rarito.movietvshow4.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {

    private String title;
    private Number voteAverage;
    private Number popularity;
    private String language;
    private String releaseDate;
    private String poster;
    private String overview;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Number getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Number voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Number getPopularity() {
        return popularity;
    }

    public void setPopularity(Number popularity) {
        this.popularity = popularity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeSerializable(this.voteAverage);
        dest.writeSerializable(this.popularity);
        dest.writeString(this.language);
        dest.writeString(this.releaseDate);
        dest.writeString(this.poster);
        dest.writeString(this.overview);
    }

    public Movie(JSONObject objMovie) {
        try {
            String title = objMovie.getString("title");
            Double voteAverage = objMovie.getDouble("vote_average");
            Double popularity = objMovie.getDouble("popularity");
            String language = objMovie.getString("original_language");
            String release = objMovie.getString("release_date");
            String poster = ("https://image.tmdb.org/t/p/original" + objMovie.getString("poster_path"));
            String overview = objMovie.getString("overview");

            this.title = title;
            this.voteAverage = voteAverage;
            this.popularity = popularity;
            this.language = language;
            this.releaseDate = release;
            this.poster = poster;
            this.overview = overview;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Movie(Parcel in) {
        this.title = in.readString();
        this.voteAverage = (Number) in.readSerializable();
        this.popularity = (Number) in.readSerializable();
        this.language = in.readString();
        this.releaseDate = in.readString();
        this.poster = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
