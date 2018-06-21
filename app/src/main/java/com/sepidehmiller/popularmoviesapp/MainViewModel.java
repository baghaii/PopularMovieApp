package com.sepidehmiller.popularmoviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sepidehmiller.popularmoviesapp.database.AppDatabase;
import com.sepidehmiller.popularmoviesapp.database.FavoriteEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

  private LiveData<List<FavoriteEntry>> favoriteMovies;

  public MainViewModel(@NonNull Application application) {
    super(application);
    AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
    favoriteMovies = appDatabase.favoriteDao().loadAllFavorites();
  }

  public LiveData<List<FavoriteEntry>> getFavoriteMovies() {
    return favoriteMovies;
  }
}
