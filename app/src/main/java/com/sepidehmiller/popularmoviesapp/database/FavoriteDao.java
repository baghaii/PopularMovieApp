package com.sepidehmiller.popularmoviesapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

  @Query("SELECT * FROM favorites")
  LiveData<List<FavoriteEntry>> loadAllFavorites();

  @Query("SELECT * FROM favorites where id= :movie_id")
  FavoriteEntry loadMovieEntry(int movie_id);

  @Insert
  void insertFavorite(FavoriteEntry favoriteEntry);

  @Update(onConflict = OnConflictStrategy.REPLACE)
  void updateFavorite(FavoriteEntry favoriteEntry);

  @Delete
  void deleteFavorite(FavoriteEntry favoriteEntry);

}
