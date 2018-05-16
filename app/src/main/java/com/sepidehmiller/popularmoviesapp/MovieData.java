package com.sepidehmiller.popularmoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieData implements Parcelable {

  private static final String BASE_PATH = "http://image.tmdb.org/t/p/w185/";

  @SerializedName("title")
  private final String mTitle;

  @SerializedName("release_date")
  private final String mReleaseDate;

  @SerializedName("vote_average")
  private final double mVoteAverage;

  @SerializedName("poster_path")
  private final String mPosterPath;

  @SerializedName("overview")
  private final String mOverview;

  public MovieData(String title, String releaseDate, double voteAverage, String posterPath, String overview) {
    mTitle = title;
    mReleaseDate = releaseDate;
    mVoteAverage = voteAverage;
    mPosterPath = posterPath;
    mOverview = overview;
  }

  //http://www.vogella.com/tutorials/AndroidParcelable/article.html
  public MovieData(Parcel in) {
    mTitle = in.readString();
    mReleaseDate = in.readString();
    mVoteAverage = in.readDouble();
    mPosterPath = in.readString();
    mOverview = in.readString();

  }

  public String getTitle() {
    return mTitle;
  }

  public String getReleaseDate() {
    return mReleaseDate;
  }

  public String getPosterPath() {
    return mPosterPath;
  }

  public String getOverview() {
    return mOverview;
  }

  public double getVoteAverage() {
    return mVoteAverage;
  }

  public String getSmallPosterUrl() {
    return BASE_PATH + getPosterPath();
  }

  /* writeToParcel and describeContents are used to write parcels. */
  @Override
  public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(mTitle);
      dest.writeString(mReleaseDate);
      dest.writeDouble(mVoteAverage);
      dest.writeString(mPosterPath);
      dest.writeString(mOverview);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
    public MovieData createFromParcel(Parcel in) {
      return new MovieData(in);
    }

    public MovieData[] newArray(int size) {
      return new MovieData[size];
    }
  };
}
