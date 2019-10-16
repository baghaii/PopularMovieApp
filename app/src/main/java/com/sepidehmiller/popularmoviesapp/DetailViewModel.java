package com.sepidehmiller.popularmoviesapp;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

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
