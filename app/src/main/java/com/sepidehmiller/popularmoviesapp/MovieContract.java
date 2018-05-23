package com.sepidehmiller.popularmoviesapp;

import android.provider.BaseColumns;

public class MovieContract {

  public static final class MovieEntry implements BaseColumns {

    // These are the columns for our database. It will have an automatically generated _ID column.
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_FAVORITE = "favorite";
    public static final String COLUMN_MOVIE_ID = "movie_id";

  }

}
