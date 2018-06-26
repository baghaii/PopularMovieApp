package com.sepidehmiller.popularmoviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sepidehmiller.popularmoviesapp.database.AppDatabase;

public class DetailViewModel extends ViewModel {
  private LiveData<MovieData> favorite;

  public DetailViewModel(AppDatabase db, int id) {
    favorite = db.favoriteDao().loadMovieEntry(id);
  }

  public LiveData<MovieData> getFavorite() {
    return favorite;
  }


}
