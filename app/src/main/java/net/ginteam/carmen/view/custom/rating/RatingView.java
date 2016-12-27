package net.ginteam.carmen.view.custom.rating;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import net.ginteam.carmen.R;

/**
 * Created by Eugene on 12/22/16.
 */

public class RatingView extends View {

    private static final int DEFAULT_CIRCLES_COUNT = 5;
    private static final int DEFAULT_CIRCLES_COLOR = Color.parseColor("#B8E986");
    private static final int DEFAULT_CIRCLES_SPACING = 0;
    private static final int DEFAULT_RATING = 0;

    private Context mContext;

    private boolean mIsMeasureCalculated;

    private int mCirclesCount;
    private int mCirclesColor;
    private float mCirclesSpacing;
    private int mRating;

    private float mCirclesRadius;
    private float mCircleY;
    private float mCircleX;

    private Paint mEmptyCirclePaint;
    private Paint mFillCirclePaint;

    public RatingView(Context context) {
        super(context);
        initializeRatingView(context, null);
    }

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeRatingView(context, attrs);
    }

    public RatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeRatingView(context, attrs);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public RatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeRatingView(context, attrs);
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mIsMeasureCalculated) {
            calculateViewMeasure();
        }

        mCircleX = mCirclesRadius + mCirclesSpacing;
        for (int i = 0; i < mCirclesCount; i++) {
            canvas.drawCircle(mCircleX, mCircleY, mCirclesRadius, i < mRating ? mFillCirclePaint : mEmptyCirclePaint);
            mCircleX += mCirclesRadius * 2 + mCirclesSpacing;
        }
    }

    private void initializeRatingView(Context context, AttributeSet attributeSet) {
        mContext = context;
        mIsMeasureCalculated = false;

        mCirclesCount = DEFAULT_CIRCLES_COUNT;
        mCirclesColor = DEFAULT_CIRCLES_COLOR;
        mCirclesSpacing = DEFAULT_CIRCLES_SPACING;
        mRating = DEFAULT_RATING;

        TypedArray attributes = context.obtainStyledAttributes(attributeSet, R.styleable.RatingView, 0, 0);
        try {
            mCirclesCount = attributes.getInteger(R.styleable.RatingView_circlesCount, DEFAULT_CIRCLES_COUNT);
            mCirclesColor = attributes.getColor(R.styleable.RatingView_circlesColor, DEFAULT_CIRCLES_COLOR);
            mCirclesSpacing = attributes.getDimension(R.styleable.RatingView_circlesSpacing, DEFAULT_CIRCLES_SPACING);
            mRating = attributes.getInteger(R.styleable.RatingView_rating, DEFAULT_RATING);
        } finally {
            attributes.recycle();
        }

        initializeCirclesPaints();
    }

    private void initializeCirclesPaints() {
        mEmptyCirclePaint = new Paint();
        mEmptyCirclePaint.setStyle(Paint.Style.STROKE);
        mEmptyCirclePaint.setColor(mCirclesColor);
        mEmptyCirclePaint.setAntiAlias(true);

        mFillCirclePaint = new Paint();
        mFillCirclePaint.setStyle(Paint.Style.FILL);
        mFillCirclePaint.setColor(mCirclesColor);
        mFillCirclePaint.setAntiAlias(true);
    }

    private void calculateViewMeasure() {
        int leftPadding = getPaddingLeft();
        int rightPadding = getPaddingRight();
        int topPadding = getPaddingTop();
        int bottomPadding = getPaddingBottom();

        float usableWidth = getWidth()
                - (leftPadding + rightPadding)
                - (mCirclesSpacing * mCirclesCount)
                - mCirclesSpacing;
        int usableHeight = getHeight() - (topPadding + bottomPadding);

        mCirclesRadius = Math.min(usableHeight, usableWidth) / 2;
        mCircleY = topPadding + (usableHeight / 2);
        mEmptyCirclePaint.setStrokeWidth(mCirclesRadius / 10);

        mIsMeasureCalculated = true;
    }

}