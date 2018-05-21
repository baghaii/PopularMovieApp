package com.sepidehmiller.popularmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAPIResults.DataAcquiredListener  {

  private static final String TAG = "MainActivity";
  private static final String MOVIE_DATA = "MovieData";
  private static final String SORT_ORDER = "SortOrder";
  private SharedPreferences mSharedPreferences;

  private String mSortOrder="popular";

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

    mMovieAdapter = new MovieAdapter(new ArrayList<MovieData>());
    mRecyclerView.setAdapter(mMovieAdapter);

    Call<MovieAPIResults> call = NetworkUtils.buildAPICall(mSortOrder);

    callAPI(call);

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
    inflater.inflate(R.menu.menu, menu);

    MenuItem popular = menu.getItem(0);
    MenuItem top_rated = menu.getItem(1);

    if (mSharedPreferences.contains(SORT_ORDER)) {
      String sort = mSharedPreferences.getString(SORT_ORDER, NetworkUtils.POPULAR);

      if (sort.contentEquals(NetworkUtils.TOP_RATED)) {
        mSortOrder = NetworkUtils.TOP_RATED;
        top_rated.setChecked(true);
      } else {
        mSortOrder = NetworkUtils.POPULAR;
        popular.setChecked(true);
      }

      Call<MovieAPIResults> call = NetworkUtils.buildAPICall(mSortOrder);
      callAPI(call);
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

  // The adapter and holder classes were informed by examples in Chapter 24 of the book mentioned
  // above.

  //https://piercezaifman.com/click-listener-for-recyclerview-adapter/

  public interface RecyclerViewClickListener {
    void onClick(View view, int i);
  }

  private class MovieHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
    private final ImageButton mImageButton;
    private RecyclerViewClickListener mRecyclerViewClickListener;


    public MovieHolder(View itemView, RecyclerViewClickListener listener) {
      super(itemView);

      mImageButton = itemView.findViewById(R.id.list_item_movie_button);
      mRecyclerViewClickListener = listener;
      mImageButton.setOnClickListener(this);
    }

    public void bindDrawable(Drawable drawable, final MovieData movie) {

      /*
        The placeholder imagery used in this app comes from
        https://www.pexels.com/photo/board-cinema-cinematography-clapper-board-274937/ */

      // https://stackoverflow.com/questions/20823249/resize-image-to-full-width-and-fixed-height-with-picasso
      Picasso.get()
          .load(movie.getSmallPosterUrl())
          .fit()
          .centerCrop()
          .placeholder(drawable)
          .error(drawable)
          .into(mImageButton);

    }

    @Override
    public void onClick(View v) {
      mRecyclerViewClickListener.onClick(v, getAdapterPosition());
    }
  }

  private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    private List<MovieData> mMovies;

    public MovieAdapter(List<MovieData> movies) {
      mMovies = movies;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      View view = inflater.inflate(R.layout.list_item_movie, parent, false);

      RecyclerViewClickListener listener = new RecyclerViewClickListener() {
        @Override
        public void onClick(View view, int i) {
          Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
          intent.putExtra(MOVIE_DATA, mMovies.get(i));
          startActivity(intent);
        }
      };

      return new MovieHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
      Drawable placeholder = getResources().getDrawable(R.drawable.cinema);
      holder.bindDrawable(placeholder, mMovies.get(position));
    }

    @Override
    public int getItemCount() {
      return mMovies.size();
    }

    public void setMovies(List<MovieData> movies) {
      mMovies = movies;
    }
  }

  /*
     This is part of the design pattern where we have created an
     MovieAPIResults.DataAcquiredListener interface and implemented
     that interface here to listen for the asynchronous task to return.
   */

  @Override
  public void onMovieDataAcquired(List<MovieData> movies) {
    mMovieAdapter.setMovies(movies);
    mMovieAdapter.notifyDataSetChanged();
  }
}
