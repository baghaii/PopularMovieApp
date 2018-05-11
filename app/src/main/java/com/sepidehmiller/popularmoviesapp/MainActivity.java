package com.sepidehmiller.popularmoviesapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MovieAPIResults.DataAcquiredListener  {

  //TODO - Use RatingBar on DetailActivity

  private static final String TAG = "MainActivity";
  private static final String API_KEY = "insert api key here";
  private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

  private String mSortOrder="popular";

  private MovieAdapter mMovieAdapter;
  private RecyclerView mRecyclerView;


  // The default imagery used in this app comes from
  // https://www.pexels.com/photo/board-cinema-cinematography-clapper-board-274937/

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    //Chapter 18 p 327 of Android Programming: Big Nerd Ranch Guide 2 ed
    mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    mMovieAdapter = new MovieAdapter(new ArrayList<MovieData>());
    mRecyclerView.setAdapter(mMovieAdapter);

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    MovieAPIInterface movieAPIInterface = retrofit.create(MovieAPIInterface.class);

    Call<MovieAPIResults> call = movieAPIInterface.getMovieData(mSortOrder, API_KEY);

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


  // The adapter and holder classes were informed by examples in Chapter 24 of the book mentioned
  // above.

  private class MovieHolder extends RecyclerView.ViewHolder {
    private ImageButton mImageButton;

    public MovieHolder(View itemView) {
      super(itemView);

      mImageButton = (ImageButton) itemView.findViewById(R.id.list_item_movie_button);

    }

    public void bindDrawable(Drawable drawable, String url) {

      Picasso.get()
          .load(url)
          .fit()
          .placeholder(drawable)
          .into(mImageButton);

      //TODO - Create an Intent. Pass Data to it.
      mImageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent i = new Intent();
        }
      });
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
      return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
      Drawable placeholder = getResources().getDrawable(R.drawable.cinema);
      holder.bindDrawable(placeholder, mMovies.get(position).getSmallPosterUrl());
    }

    @Override
    public int getItemCount() {
      return mMovies.size();
    }

    public void setMovies(List<MovieData> movies) {
      mMovies = movies;
    }
  }

  @Override
  public void onMovieDataAcquired(List<MovieData> movies) {
    mMovieAdapter.setMovies(movies);
    mMovieAdapter.notifyDataSetChanged();
  }
}
