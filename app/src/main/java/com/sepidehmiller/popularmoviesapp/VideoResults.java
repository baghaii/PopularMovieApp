package com.sepidehmiller.popularmoviesapp;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

public class VideoResults {
  @SerializedName("results")
  private JsonArray mResults;

  // key in the results array is what is given to YouTube.
  /* Ex. https://www.youtube.com/watch?v=GpAuCG6iUcA
  id: "59b5e37bc3a3682b0a0049e6",
  iso_639_1: "en",
  iso_3166_1: "US",
  key: "GpAuCG6iUcA",
  name: "Teaser",
  site: "YouTube",
  size: 1080,
  type: "Teaser" */


}
