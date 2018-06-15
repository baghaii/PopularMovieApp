package com.sepidehmiller.popularmoviesapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteEntry {

  @PrimaryKey
  private int id;
  @ColumnInfo(name="image_path")
  private String imagePath;
  private String title;

  @ColumnInfo(name="vote_average")
  private double voteAverage;

  @ColumnInfo(name="release_date")
  private String releaseDate;

  private String overview;

  public FavoriteEntry(int id, String title,
                       String imagePath,
                       double voteAverage,
                       String releaseDate,
                       String overview) {

    this.id = id;
    this.title = title;
    this.imagePath = imagePath;
    this.voteAverage = voteAverage;
    this.releaseDate = releaseDate;
    this.overview = overview;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getImagePath() {
    return imagePath;
  }

  public double getVoteAverage() {
    return voteAverage;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public String getOverview() {
    return overview;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setImageName(String imagePath) {
    this.imagePath = imagePath;
  }

  public void setVoteAverage(int voteAverage) {
    this.voteAverage = voteAverage;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }
}
