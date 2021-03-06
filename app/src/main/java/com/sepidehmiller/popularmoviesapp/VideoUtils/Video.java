package com.sepidehmiller.popularmoviesapp.VideoUtils;

public class Video {

  // This class specifies the individual video used to create the sVideos ArrayList in VideoResults

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

  private String key;
  private String name;
  private String site;
  private String type;

  public String getName() {
    return name;
  }

  public String getKey() { return key; }

}
