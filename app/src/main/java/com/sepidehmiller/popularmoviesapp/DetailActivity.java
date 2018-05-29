package com.sepidehmiller.popularmoviesapp;

import android.content.ContentValues;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

  private static final String TAG = "DetailActivity";
  private static final String MOVIE_DATA = "MovieData";

  private TextView mTitleTextView;
  private TextView mReleaseDateTextView;
  private TextView mSynopsisTextView;
  private RatingBar mRatingBar;
  private ImageView mImageView;
  private MovieData mMovie;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    mTitleTextView = findViewById(R.id.titleTextView);
    mReleaseDateTextView = findViewById(R.id.releaseDataTextView);
    mSynopsisTextView = findViewById(R.id.synopsisTextView);
    mSynopsisTextView.setMovementMethod(new ScrollingMovementMethod());
    mRatingBar = findViewById(R.id.ratingBar);
    mRatingBar.setIsIndicator(true);
    mRatingBar.setNumStars(5);
    mRatingBar.setStepSize((float)0.1);
    mImageView = findViewById(R.id.imageView);
    Stetho.initializeWithDefaults(this);



    Bundle data = getIntent().getExtras();
    if (data != null && !data.isEmpty()) {
      mMovie = data.getParcelable(MOVIE_DATA);
      mTitleTextView.setText(mMovie.getTitle());
      mReleaseDateTextView.setText(mMovie.getReleaseDate());
      mSynopsisTextView.setText(mMovie.getOverview());
      mRatingBar.setRating((float) mMovie.getVoteAverage()/ (float) 2.0);

      Picasso.get()
          .load(mMovie.getSmallPosterUrl())
          .fit()
          .centerCrop()
          .placeholder(R.drawable.cinema)
          .error(R.drawable.cinema)
          .into(mImageView);
      Log.i(TAG, String.valueOf((float) mMovie.getVoteAverage()));
    }

  }

  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_detail, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()) {
      case R.id.menu_item_favorite:
        changeIconColor(item);
        addMovieToDb();
        return true;
      default:
        return false;
    }
  }

  public void addMovieToDb() {
    ContentValues cv = new ContentValues();
    cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getId());

    //Sqlite doesn't have boolean values. Using one for true, zero for false
    cv.put(MovieContract.MovieEntry.COLUMN_FAVORITE, 1);

    Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, cv);

    if (uri != null) {
      Toast.makeText(this, uri.toString(),Toast.LENGTH_LONG).show();
    }

  }

  public void changeIconColor(MenuItem item) {
    Drawable icon = item.getIcon();
    Drawable newIcon = icon.mutate();
    /*
      How do we change icon colors?
      https://stackoverflow.com/questions/32924986/change-fill-color-on-vector-asset-in-android-studio
     */
    if (!mMovie.isFavorite()) {
      DrawableCompat.setTint(newIcon, getResources().getColor(R.color.colorAccent));
      item.setIcon(newIcon);
      mMovie.setFavorite(true);
    } else {
      DrawableCompat.setTint(newIcon, getResources().getColor(R.color.white));
      mMovie.setFavorite(false);
    }

    DrawableCompat.setTintMode(newIcon, PorterDuff.Mode.SRC_IN);
    item.setIcon(newIcon);
  }

}
