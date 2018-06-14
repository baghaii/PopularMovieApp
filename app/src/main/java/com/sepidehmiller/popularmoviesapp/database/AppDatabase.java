package com.sepidehmiller.popularmoviesapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {FavoriteEntry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
  private static final String TAG = AppDatabase.class.getSimpleName();
  private static final Object LOCK = new Object();
  private static final String DATABASE_NAME = "favorites";
  private static AppDatabase sInstance;

  public static AppDatabase getInstance(Context context) {
    if (sInstance == null) {
      synchronized (LOCK) {
        Log.d(TAG, "Creating new databse instance");
        sInstance = Room.databaseBuilder(context.getApplicationContext(),
            AppDatabase.class, AppDatabase.DATABASE_NAME)
            //TODO - This is very bad, but we are doing it only temporarily
            .allowMainThreadQueries()
            .build();
      }
    }
    Log.d(TAG, "Getting database instance");
    return sInstance;
  }

  public abstract FavoriteDao favoriteDao();
}