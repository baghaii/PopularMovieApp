package com.sepidehmiller.popularmoviesapp;

import com.google.gson.annotations.SerializedName;

public class MovieData {

  private static final String BASE_PATH = " http://image.tmdb.org/t/p/w185/";

  @SerializedName("title")
  private String mTitle;

  @SerializedName("release_date")
  private String mReleaseDate;

  @SerializedName("vote_average")
  private double mVoteAverage;

  @SerializedName("poster_path")
  private String mPosterPath;

  @SerializedName("overview")
  private String mOverview;

  public MovieData(String title, String releaseDate, double voteAverage, String posterPath, String overview) {
    mTitle = title;
    mReleaseDate = releaseDate;
    mVoteAverage = voteAverage;
    mPosterPath = posterPath;
    mOverview = overview;
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
}
