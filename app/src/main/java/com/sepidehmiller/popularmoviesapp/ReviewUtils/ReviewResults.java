package com.sepidehmiller.popularmoviesapp.ReviewUtils;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewResults {
  @SerializedName("results")
  private JsonArray mResults;

  private static List<Review> mReviewList = new ArrayList<Review>();

  public interface ReviewsAcquiredListener {
    void onReviewsAcquired(List<Review> reviews);
  }
}
