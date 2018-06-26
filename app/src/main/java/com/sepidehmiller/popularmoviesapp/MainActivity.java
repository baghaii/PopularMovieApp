package com.sepidehmiller.popularmoviesapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sepidehmiller.popularmoviesapp.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  private static final String SORT_ORDER = "SortOrder";
  private static final String FAVORITE = "favorite";
  private static final String RECYCLER_POSITION = "RecyclerViewPosition";

  private AppDatabase mDb;

  private SharedPreferences mSharedPreferences;

  private String mSortOrder;

  private MovieAdapter mMovieAdapter;
  private RecyclerView mRecyclerView;
  private Parcelable recyclerPosition;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    mRecyclerView = findViewById(R.id.recycler_view);

    /*
      How do you determine phone orientation in code?
      https://stackoverflow.com/questions/2795833/check-orientation-on-android-phone#2799001 */

    if (getResources().getConfiguration().orientation ==
        Configuration.ORIENTATION_PORTRAIT) {

      /*
          Chapter 18 p 327 of Android Programming: Big Nerd Ranch Guide 2 ed for setting
          RecyclerView GridLayoutManager.
       */
      mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    } else {
      mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }

    mMovieAdapter = new MovieAdapter(getApplicationContext(), new ArrayList<MovieData>());
    mRecyclerView.setAdapter(mMovieAdapter);

    mSortOrder = mSharedPreferences.getString(SORT_ORDER, NetworkUtils.POPULAR);

    mDb = AppDatabase.getInstance(getApplicationContext());

    setupViewModel();


  }

  // Reviewer recommended saving RecyclerView position
  // https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(RECYCLER_POSITION,
        mRecyclerView.getLayoutManager().onSaveInstanceState());
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    if (savedInstanceState != null && savedInstanceState.containsKey(RECYCLER_POSITION)) {
      recyclerPosition = savedInstanceState.getParcelable(RECYCLER_POSITION);
    }
  }


  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);

    MenuItem popular = menu.getItem(0);
    MenuItem top_rated = menu.getItem(1);
    MenuItem favorite = menu.getItem(2);


    // Check the correct radio button in the menu.
    switch (mSortOrder) {
      case FAVORITE:
        favorite.setChecked(true);
        break;
      case NetworkUtils.TOP_RATED:
        top_rated.setChecked(true);
        break;
      case NetworkUtils.POPULAR:
        popular.setChecked(true);
        break;
      default:
        popular.setChecked(true);
        break;
    }

    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    Call<MovieAPIResults> call;
    SharedPreferences.Editor editor = mSharedPreferences.edit();
    item.setChecked(true);

    switch (item.getItemId()) {
      case R.id.menu_item_popular:
        mSortOrder = NetworkUtils.POPULAR;
        break;
      case R.id.menu_item_top_rated:
        mSortOrder = NetworkUtils.TOP_RATED;
        break;
      case R.id.menu_item_favorite:
        mSortOrder = FAVORITE;
        break;
      default:
        return false;
    }

    editor.putString(SORT_ORDER, mSortOrder);
    editor.apply();
    setupViewModel();
    return true;
  }


  @Override
  protected void onResume() {
    super.onResume();

    if (mSortOrder.contentEquals(FAVORITE)) {
      setupViewModel();
    }
  }

  public void setupViewModel() {

    MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);


    viewModel.getFavoriteMovies().observe(this, new Observer<List<MovieData>>() {
      @Override
      public void onChanged(@Nullable List<MovieData> favoriteEntries) {
        Log.d(TAG, "Receiving changes from LiveData");

        if (mSortOrder.contentEquals(FAVORITE)) {
          List<MovieData> movieList = new ArrayList<MovieData>();

          if (favoriteEntries != null) {
            for (MovieData fave : favoriteEntries) {
              fave.setFavorite(1);
            }
            setAdapter(favoriteEntries);
          }
        }
      }
    });

    viewModel.getTopRatedMovies().observe(this, new Observer<List<MovieData>>() {
      @Override
      public void onChanged(@Nullable List<MovieData> movieData) {
        if (movieData != null && mSortOrder.contentEquals(NetworkUtils.TOP_RATED)) {
          setAdapter(movieData);
        }
      }
    });

    viewModel.getPopularMovies().observe(this, new Observer<List<MovieData>>() {
      @Override
      public void onChanged(@Nullable List<MovieData> movieData) {
        if (movieData != null && mSortOrder.contentEquals(NetworkUtils.POPULAR)) {
          setAdapter(movieData);
        }
      }
    });
  }

  public void setAdapter(List<MovieData> movies) {
    mMovieAdapter.setMovies(movies);
    mMovieAdapter.notifyDataSetChanged();
    if (recyclerPosition != null) {
      mRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerPosition);
    }
  }

}
