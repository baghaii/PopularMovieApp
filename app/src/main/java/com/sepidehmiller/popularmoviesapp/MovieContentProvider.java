package com.sepidehmiller.popularmoviesapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static com.sepidehmiller.popularmoviesapp.MovieContract.MovieEntry.*;

public class MovieContentProvider extends ContentProvider {

  public static final int MOVIES = 100;
  public static final int MOVIE_WITH_ID = 101;

  public static final UriMatcher sUriMatcher = buildUriMatcher();

  public static UriMatcher buildUriMatcher() {
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
    uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);

    return uriMatcher;
  };


  private MovieDbHelper mMovieDbHelper;

  @Override
  public boolean onCreate() {
    Context context = getContext();
    mMovieDbHelper = new MovieDbHelper(context);
    return true;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
    int matcher = sUriMatcher.match(uri);

    Cursor retCursor;

    switch(matcher) {
      case MOVIES:
        retCursor = db.query(TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
            );
        break;
      case MOVIE_WITH_ID:
        String id = uri.getPathSegments().get(2);
        String mSelection = COLUMN_MOVIE_ID+"=?";
        String[] mSelectionArgs = new String[]{id};
        retCursor = db.query(TABLE_NAME,
           projection,
            mSelection,
            mSelectionArgs,
            null,
            null,
            sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    retCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return retCursor;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
    int match = sUriMatcher.match(uri);

    Uri returnedUri;

    switch(match) {
      case MOVIES:
        long id = db.insert(TABLE_NAME, null, values);
        if (id > 0) {
          returnedUri = ContentUris.withAppendedId(CONTENT_URI, id);
        } else {
          throw new SQLException("Failed to insert row into " + uri);
        }
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);
    return returnedUri;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }
}
