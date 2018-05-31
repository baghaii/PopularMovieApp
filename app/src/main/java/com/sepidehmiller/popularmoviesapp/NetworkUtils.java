package com.sepidehmiller.popularmoviesapp;

import com.sepidehmiller.popularmoviesapp.VideoUtils.VideoResults;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
  private static final String API_KEY = "insert api key here";
  private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

  public static final String POPULAR = "popular";
  public static final String TOP_RATED = "top_rated";

  // Use retrofit to build the API call.
  // https://github.com/codepath/android_guides/wiki/Consuming-APIs-with-Retrofit

  public static Call<MovieAPIResults> buildAPICall(String sortOrder) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    MovieAPIInterface movieAPIInterface = retrofit.create(MovieAPIInterface.class);
    Call<MovieAPIResults> call = movieAPIInterface.getMovieData(sortOrder, API_KEY);

    return call;
  }

  public static Call<VideoResults> buildVideoCall(int videoId) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    MovieAPIInterface movieInterface = retrofit.create(MovieAPIInterface.class);
    Call<VideoResults> call = movieInterface.getVideoData(videoId, API_KEY);

    return call;
  }
}
