package com.sepidehmiller.popularmoviesapp;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sepidehmiller.popularmoviesapp.database.AppDatabase;
import com.sepidehmiller.popularmoviesapp.database.FavoriteEntry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAPIResults.DataAcquiredListener  {

  private static final String TAG = "MainActivity";
  private static final String SORT_ORDER = "SortOrder";
  private static final String FAVORITE = "favorite";

  private AppDatabase mDb;

  private SharedPreferences mSharedPreferences;

  private String mSortOrder;

  private MovieAdapter mMovieAdapter;
  private RecyclerView mRecyclerView;


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

    if (!mSortOrder.contentEquals(FAVORITE)) {
      Call<MovieAPIResults> call = NetworkUtils.buildAPICall(mSortOrder);
      callAPI(call);
    } else {
      loadFavorites();
    }


  }

  public void callAPI(Call<MovieAPIResults> call) {
    // Call the API Asynchronously
    call.enqueue(new Callback<MovieAPIResults>() {

      @Override
      public void onResponse(Call<MovieAPIResults> call, Response<MovieAPIResults> response) {

        if (response.message().contentEquals("OK") ) {
          onMovieDataAcquired(response.body().getMovieDataList());
        } else {
          Log.e(TAG, "Something unexpected happened to our request: " + response.message());
        }
      }

      @Override
      public void onFailure(Call<MovieAPIResults> call, Throwable t) {
        Log.e(TAG, t.getMessage());
      }
    });
  }

  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);

    MenuItem popular = menu.getItem(0);
    MenuItem top_rated = menu.getItem(1);
    MenuItem favorite = menu.getItem(2  );


    // Check the correct radio button in the menu.
    switch(mSortOrder) {
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
    switch(item.getItemId()) {
      case R.id.menu_item_popular:
        toggleMenuOption(item);
        mSortOrder = NetworkUtils.POPULAR;
        editor.putString(SORT_ORDER, mSortOrder);
        editor.apply();
        call = NetworkUtils.buildAPICall(mSortOrder);
        callAPI(call);
        return true;
      case R.id.menu_item_top_rated:
        toggleMenuOption(item);
        mSortOrder = NetworkUtils.TOP_RATED;
        editor.putString(SORT_ORDER, mSortOrder);
        editor.apply();
        call = NetworkUtils.buildAPICall(mSortOrder);
        callAPI(call);
        return true;
      case R.id.menu_item_favorite:
        toggleMenuOption(item);
        mSortOrder = FAVORITE;
        editor.putString(SORT_ORDER, mSortOrder);
        editor.apply();
        loadFavorites();
        return true;
      default:
        return false;
    }
  }

  public void toggleMenuOption(MenuItem item) {
    if (item.isChecked()) {
      item.setChecked(false);
    } else {
      item.setChecked(true);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    if (mSortOrder.contentEquals(FAVORITE)) {
      loadFavorites();
    }
  }

  public void loadFavorites() {

    AppExecutors.getInstance().diskIO().execute(new Runnable() {
      @Override
      public void run() {
        final List<FavoriteEntry> favorites =
            mDb.favoriteDao().loadAllFavorites();
        List<MovieData> movieList = new ArrayList<MovieData>();

        for (FavoriteEntry fave : favorites) {
              movieList.add(new MovieData(fave));
        }

        final List<MovieData> movies = movieList;

        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            mMovieAdapter.setMovies(movies);
            mMovieAdapter.notifyDataSetChanged();
          }
        });
      }
    });

  }

  @Override
  public void onMovieDataAcquired(List<MovieData> movies) {
    mMovieAdapter.setMovies(movies);
    mMovieAdapter.notifyDataSetChanged();
  }
}
