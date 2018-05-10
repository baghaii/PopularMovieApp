package com.sepidehmiller.popularmoviesapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPIInterface {

  @GET("{sortOrder}")
  Call<ArrayList<MovieData>> getMovieData(
    @Path("sortOrder") String sortOrder,
    @Query("api_key") String apiKey
  );

}
