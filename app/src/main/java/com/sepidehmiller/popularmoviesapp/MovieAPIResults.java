package com.sepidehmiller.popularmoviesapp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MovieAPIResults {
  @SerializedName("results")
  private JsonArray mResults;

  private static List<MovieData> mMovieDataList = new ArrayList<MovieData>();


  public List<MovieData> getMovieDataList() {

    Gson gson = new Gson();
    //http://blog.nkdroidsolutions.com/how-to-parsing-json-array-using-gson-in-android-tutorial/
    mMovieDataList = gson.fromJson(mResults, new TypeToken<List<MovieData>>(){}.getType());

    return mMovieDataList;
  }

  public interface DataAcquiredListener {
    void onMovieDataAcquired(List<MovieData> movies);
  }

}
