package com.sepidehmiller.popularmoviesapp.database;

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
  List <FavoriteEntry> loadAllFavorites();

  @Insert
  void insertFavorite(FavoriteEntry favoriteEntry);

  @Update(onConflict = OnConflictStrategy.REPLACE)
  void updateFavorite(FavoriteEntry favoriteEntry);

  @Delete
  void deleteFavorite(FavoriteEntry favoriteEntry);

}
