package com.example.aamirkalimi.popularmoviestage2;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewList implements Parcelable {
    private String author, reviewContent;
    public ReviewList(String author, String reviewContent){
        this.author = author;
        this.reviewContent = reviewContent;
    }

    private ReviewList(Parcel parcel) {
        author = parcel.readString();
        reviewContent = parcel.readString();
    }

    public static final Creator<ReviewList> CREATOR = new Creator<ReviewList>() {
        @Override
        public ReviewList createFromParcel(Parcel in) {
            return new ReviewList(in);
        }

        @Override
        public ReviewList[] newArray(int size) {
            return new ReviewList[size];
        }
    };

    public String getAuthor() {
        return author;
    }
    public String getReviewContent() {
        return reviewContent;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(author);
        parcel.writeString(reviewContent);
    }
}
