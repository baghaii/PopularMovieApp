package com.sepidehmiller.popularmoviesapp.ReviewUtils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sepidehmiller.popularmoviesapp.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

  private List<Review> mReviews;

  public ReviewAdapter(List<Review> reviews) {
    mReviews = reviews;
  }

  @NonNull
  @Override
  public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View reviewView = inflater.inflate(R.layout.list_item_review, parent, false);
    return new ReviewHolder(reviewView);
  }

  //TODO - Maybe use spannables to make this less ugly.

  @Override
  public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
    Review review = mReviews.get(position);
    holder.mReviewTextView.setText(review.getAuthor() + ": " + review.getContent());
  }

  @Override
  public int getItemCount() {
    return mReviews.size();
  }

  public class ReviewHolder extends RecyclerView.ViewHolder {
    public TextView mReviewTextView;

    public ReviewHolder(View itemView) {
      super(itemView);
      mReviewTextView = itemView.findViewById(R.id.list_item_review_textview);
      mReviewTextView.setMovementMethod(new ScrollingMovementMethod());
    }
  }
}
