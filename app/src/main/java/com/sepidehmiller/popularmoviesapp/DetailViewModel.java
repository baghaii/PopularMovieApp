package com.sepidehmiller.popularmoviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sepidehmiller.popularmoviesapp.database.AppDatabase;
import com.sepidehmiller.popularmoviesapp.database.FavoriteEntry;

public class DetailViewModel extends ViewModel {
  private LiveData<FavoriteEntry> favorite;

  public DetailViewModel(AppDatabase db, int id) {
    favorite = db.favoriteDao().loadMovieEntry(id);
  }

  public LiveData<FavoriteEntry> getFavorite() {
    return favorite;
  }
}
