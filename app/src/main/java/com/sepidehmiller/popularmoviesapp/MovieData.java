package com.sepidehmiller.popularmoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sepidehmiller.popularmoviesapp.database.FavoriteEntry;

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

  @SerializedName("id")
  private final int mId;

  //Parcelable doesn't let you write a single boolean.
  //Using 0 to specify something that is not a favorite.
  //Using 1 to specify a favorite.
  private int mFavorite = 0;

  public MovieData(String title, String releaseDate, double voteAverage, String posterPath, String overview, int id) {
    mTitle = title;
    mReleaseDate = releaseDate;
    mVoteAverage = voteAverage;
    mPosterPath = posterPath;
    mOverview = overview;
    mId = id;
  }

  //TODO - Expand database to contain other fields
  public MovieData(FavoriteEntry favoriteEntry) {
    mTitle = favoriteEntry.getTitle();
    mId = favoriteEntry.getId();
    mPosterPath = favoriteEntry.getImagePath();
    mReleaseDate = null;
    mVoteAverage = 0;
    mOverview = null;
    mFavorite = 1;
  }

  //http://www.vogella.com/tutorials/AndroidParcelable/article.html
  public MovieData(Parcel in) {
    mTitle = in.readString();
    mReleaseDate = in.readString();
    mVoteAverage = in.readDouble();
    mPosterPath = in.readString();
    mOverview = in.readString();
    mId = in.readInt();
    mFavorite = in.readInt();

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

  public int getId() { return mId; }

  /* writeToParcel and describeContents are used to write parcels. */
  @Override
  public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(mTitle);
      dest.writeString(mReleaseDate);
      dest.writeDouble(mVoteAverage);
      dest.writeString(mPosterPath);
      dest.writeString(mOverview);
      dest.writeInt(mId);
      dest.writeInt(mFavorite);
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

  public int isFavorite() {
    return mFavorite;
  }

  public void setFavorite(int favorite) {
    mFavorite = favorite;
  }
}
