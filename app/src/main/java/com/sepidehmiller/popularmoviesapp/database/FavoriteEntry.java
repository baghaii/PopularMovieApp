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

  public FavoriteEntry(int id, String title, String imagePath) {
    this.id = id;
    this.title = title;
    this.imagePath = imagePath;
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

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setImageName(String imagePath) {
    this.imagePath = imagePath;
  }

}
