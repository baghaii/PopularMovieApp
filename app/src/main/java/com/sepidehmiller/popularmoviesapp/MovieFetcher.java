package com.sepidehmiller.popularmoviesapp;

import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieFetcher {

  private static final String TAG = "MovieFetcher";
  private static final String API_KEY = "insert api key here";
  private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

  public void fetchMovies() {

    // To learn about retrofit, I started with this tutorial or one much like it.
    // https://zeroturnaround.com/rebellabs/getting-started-with-retrofit-2/

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    MovieAPIInterface movieAPIInterface = retrofit.create(MovieAPIInterface.class);

    Call<ArrayList<MovieData>> call = movieAPIInterface.getMovieData("popular", API_KEY);

    // Call the API Asynchronously
    call.enqueue(new Callback<ArrayList<MovieData>>() {

      @Override
      public void onResponse(Call<ArrayList<MovieData>> call, Response<ArrayList<MovieData>> response) {

          if (response.message().contentEquals("OK")) {
            Log.i(TAG, response.toString());

          } else {
            Log.e(TAG, "Something unexpected happened to our request.");
          }
      }

      @Override
      public void onFailure(Call<ArrayList<MovieData>> call, Throwable t) {
        Log.e(TAG, t.getMessage());
      }
    });
  }
}
