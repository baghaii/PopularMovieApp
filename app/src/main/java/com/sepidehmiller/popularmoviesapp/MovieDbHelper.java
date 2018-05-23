package com.sepidehmiller.popularmoviesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sepidehmiller.popularmoviesapp.MovieContract.MovieEntry;


public class MovieDbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "moviesDb.db";

  private static final int VERSION = 1;

  MovieDbHelper(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    final String CREATE_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
        MovieEntry._ID + " INTEGER PRIMARY KEY, " +
        MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
        MovieEntry.COLUMN_FAVORITE + " INTEGER);";

    db.execSQL(CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
    onCreate(db);
  }
}
