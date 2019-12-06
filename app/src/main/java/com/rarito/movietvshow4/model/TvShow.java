package com.rarito.movietvshow4.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvShow implements Parcelable {

    private String title;
    private Number voteAverage;
    private Number popularity;
    private String language;
    private String release;
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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
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
        dest.writeString(this.release);
        dest.writeString(this.poster);
        dest.writeString(this.overview);
    }

    public TvShow(JSONObject objectTvShow) {
        try {
            String title = objectTvShow.getString("original_name");
            Double voteAverage = objectTvShow.getDouble("vote_average");
            Double popularity = objectTvShow.getDouble("popularity");
            String language = objectTvShow.getString("original_language");
            String release = objectTvShow.getString("first_air_date");
            String poster = ("https://image.tmdb.org/t/p/original" + objectTvShow.getString("poster_path"));
            String overview = objectTvShow.getString("overview");

            this.title = title;
            this.voteAverage = voteAverage;
            this.popularity = popularity;
            this.language = language;
            this.release = release;
            this.poster = poster;
            this.overview = overview;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TvShow(Parcel in) {
        this.title = in.readString();
        this.voteAverage = (Number) in.readSerializable();
        this.popularity = (Number) in.readSerializable();
        this.language = in.readString();
        this.release = in.readString();
        this.poster = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
