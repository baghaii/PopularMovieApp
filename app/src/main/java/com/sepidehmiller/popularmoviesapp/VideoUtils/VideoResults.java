package com.sepidehmiller.popularmoviesapp.VideoUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class VideoResults {

  // This is the array of videos returned in the retrofit call of DetailActivity.java

  @SerializedName("results")
  private JsonArray mResults;

  private static List<Video> sVideos = new ArrayList<Video>();


  public List<Video> getVideoList() {
    Gson gson = new Gson();

    sVideos = gson.fromJson(mResults, new TypeToken<List<Video>>(){}.getType());
    return sVideos;
  }

  public interface VideoAcquiredListener {
    void onVideosAcquired(List<Video> videos);
  }

}
