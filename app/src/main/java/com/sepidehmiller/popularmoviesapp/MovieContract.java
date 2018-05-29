package com.sepidehmiller.popularmoviesapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

  public static final String AUTHORITY = "com.sepidehmiller.popularmoviesapp";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
  public static final String PATH_MOVIES = "movies";

  public static final class MovieEntry implements BaseColumns {
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH_MOVIES)
        .build();

    // These are the columns for our database. It will have an automatically generated _ID column.
    public static final String TABLE_NAME = "movies";
    public static final String COLUMN_FAVORITE = "favorite";
    public static final String COLUMN_MOVIE_ID = "movie_id";

  }

}
