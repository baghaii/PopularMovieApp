package com.sepidehmiller.popularmoviesapp;

import com.sepidehmiller.popularmoviesapp.ReviewUtils.ReviewResults;
import com.sepidehmiller.popularmoviesapp.VideoUtils.VideoResults;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
  private static final String API_KEY = "c9df7bee96e71fa5a46367f28f9a4a70";
  private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

  public static final String POPULAR = "popular";
  public static final String TOP_RATED = "top_rated";

  private static Retrofit retrofit = new Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build();

  private static MovieAPIInterface sMovieAPIInterface = retrofit.create(MovieAPIInterface.class);

  // Use retrofit to build the API call.
  // https://github.com/codepath/android_guides/wiki/Consuming-APIs-with-Retrofit

  public static Call<MovieAPIResults> buildAPICall(String sortOrder) {

    Call<MovieAPIResults> call = sMovieAPIInterface.getMovieData(sortOrder, API_KEY);

    return call;
  }

  public static Call<VideoResults> buildVideoCall(int videoId) {

    Call<VideoResults> call = sMovieAPIInterface.getVideoData(videoId, API_KEY);

    return call;
  }

  public static Call<ReviewResults> buildReviewCall(int videoId) {
    Call<ReviewResults> call = sMovieAPIInterface.getReviewData(videoId, API_KEY);
    return call;
  }

}
