package com.example.aamirkalimi.popularmoviestage2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "moviesFav")
public class MovieList implements Parcelable {

    @PrimaryKey
    @ColumnInfo
    private long id;
    private String title, posterUrl, description, voteAverage, releaseDate;
    private MovieList(Parcel parcel) {
        id = parcel.readLong();
        title = parcel.readString();
        posterUrl = parcel.readString();
        description = parcel.readString();
        voteAverage = parcel.readString();
        releaseDate = parcel.readString();
    }
    public static final Creator<MovieList> CREATOR = new Creator<MovieList>() {

        @Override
        public MovieList createFromParcel(Parcel in) {
            return new MovieList(in);
        }

        @Override
        public MovieList[] newArray(int size) {
            return new MovieList[size];
        }
    };
    public MovieList() { }
    public MovieList(MovieList movieList) {
        this.id = movieList.getId();
        this.title = movieList.getTitle();
        this.posterUrl = movieList.getPosterUrl();
        this.description = movieList.getDescription();
        this.voteAverage = movieList.getVoteAverage();
        this.releaseDate = movieList.getReleaseDate();
    }
    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getPosterUrl() {
        return posterUrl;
    }
    public String getDescription() {
        return description;
    }
    public String getVoteAverage() {
        return voteAverage;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setVoteAverage(String vote_average) {
        this.voteAverage = vote_average;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public MovieList(long id, String title, String description, String posterUrl, String vote_average, String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.description = description;
        this.voteAverage = vote_average;
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(posterUrl);
        parcel.writeString(description);
        parcel.writeString(voteAverage);
        parcel.writeString(releaseDate);
    }
}