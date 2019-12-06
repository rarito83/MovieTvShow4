package com.rarito.movietvshow4.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShowFavorite implements Parcelable {
    private int id;
    private String title;
    private Number voteAverage;
    private Number popularity;
    private String language;
    private String releaseDate;
    private String poster;


    public TvShowFavorite(int id, String title, Number voteAverage, Number popularity, String language, String releaseDate, String poster, String overview) {
        this.id = id;
        this.title = title;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.language = language;
        this.releaseDate = releaseDate;
        this.poster = poster;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeSerializable(this.voteAverage);
        dest.writeSerializable(this.popularity);
        dest.writeString(this.language);
        dest.writeString(this.releaseDate);
        dest.writeString(this.poster);
    }

    public TvShowFavorite() {
    }

    protected TvShowFavorite(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.voteAverage = (Number) in.readSerializable();
        this.popularity = (Number) in.readSerializable();
        this.language = in.readString();
        this.releaseDate = in.readString();
        this.poster = in.readString();
    }

    public static final Parcelable.Creator<TvShowFavorite> CREATOR = new Parcelable.Creator<TvShowFavorite>() {
        @Override
        public TvShowFavorite createFromParcel(Parcel source) {
            return new TvShowFavorite(source);
        }

        @Override
        public TvShowFavorite[] newArray(int size) {
            return new TvShowFavorite[size];
        }
    };
}
