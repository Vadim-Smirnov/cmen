package net.ginteam.carmen.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.ginteam.carmen.R;
import net.ginteam.carmen.kotlin.model.RatingModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vadik on 25.01.17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter <ReviewItemViewHolder> {

    private static final int REVIEW_VISIBLE_COUNT = 2;

    private Context mContext;

    private int mReviewVisibleCount;
    private boolean mIsAllReviews;

    private List<RatingModel> mReviews;

    public ReviewsAdapter(Context context, List<RatingModel> reviews, boolean isAllReviews) {
        mContext = context;
        mReviews = reviews;
        mIsAllReviews = isAllReviews;
        mReviewVisibleCount = isAllReviews ? reviews.size() : REVIEW_VISIBLE_COUNT;
    }

    @Override
    public ReviewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View reviewItemView = LayoutInflater
                .from(parent.getContext())
                .inflate(mIsAllReviews ? R.layout.list_item_all_review
                        : R.layout.list_item_review, parent, false);
        return new ReviewItemViewHolder(reviewItemView);
    }

    @Override
    public void onBindViewHolder(ReviewItemViewHolder holder, int position) {
        RatingModel currentReview = mReviews.get(position);

        holder.getRatingView().setRating(currentReview.getTotalRating());
        if (currentReview.getTitle() != null && !currentReview.getTitle().isEmpty()) {
            holder.getTextViewReviewTitle().setVisibility(View.VISIBLE);
            holder.getTextViewReviewTitle().setText(currentReview.getTitle());
        }
        if (currentReview.getText() != null && !currentReview.getText().isEmpty()) {
            holder.getTextViewReviewText().setVisibility(View.VISIBLE);
            holder.getTextViewReviewText().setText(currentReview.getText());
        }
        holder.getTextViewUserName().setText(currentReview.getDisplayName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(currentReview.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd.MM.yyyy");
        holder.getTextViewDate().setText(format.format(date));
    }

    @Override
    public int getItemCount() {
        return mReviews.size() > mReviewVisibleCount ? mReviewVisibleCount : mReviews.size();
    }
}
