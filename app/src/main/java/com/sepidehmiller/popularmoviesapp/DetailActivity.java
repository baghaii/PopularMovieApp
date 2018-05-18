package com.sepidehmiller.popularmoviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

  private static final String TAG = "DetailActivity";
  private static final String MOVIE_DATA = "MovieData";

  private TextView mTitleTextView;
  private TextView mReleaseDateTextView;
  private TextView mSynopsisTextView;
  private RatingBar mRatingBar;
  private ImageView mImageView;

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



    Bundle data = getIntent().getExtras();
    if (data != null && !data.isEmpty()) {
      MovieData movie = data.getParcelable(MOVIE_DATA);
      mTitleTextView.setText(movie.getTitle());
      mReleaseDateTextView.setText(movie.getReleaseDate());
      mSynopsisTextView.setText(movie.getOverview());
      mRatingBar.setRating((float) movie.getVoteAverage()/ (float) 2.0);

      Picasso.get()
          .load(movie.getSmallPosterUrl())
          .fit()
          .centerCrop()
          .into(mImageView);
      Log.i(TAG, String.valueOf((float) movie.getVoteAverage()));
    }

  }
}
