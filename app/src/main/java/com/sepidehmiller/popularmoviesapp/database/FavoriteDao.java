package com.sepidehmiller.popularmoviesapp.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sepidehmiller.popularmoviesapp.MovieData;

import java.util.List;


@Dao
public interface FavoriteDao {

  @Query("SELECT * FROM favorites")
  LiveData<List<MovieData>> loadAllFavorites();

  @Query("SELECT * FROM favorites where id= :movie_id")
  LiveData<MovieData> loadMovieEntry(int movie_id);

  @Insert
  void insertFavorite(MovieData movieEntry);

  @Update(onConflict = OnConflictStrategy.REPLACE)
  void updateFavorite(MovieData movieEntry);

  @Delete
  void deleteFavorite(MovieData movieEntry);

}
