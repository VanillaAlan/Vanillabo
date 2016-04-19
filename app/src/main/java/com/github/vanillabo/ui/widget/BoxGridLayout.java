/**
 * Copyright (c) 2013 Wireless Designs, LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.vanillabo.ui.widget;

import com.github.vanillabo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BoxGridLayout extends ViewGroup {

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }
    }

    private static final int COUNT = 3;

    private int mSeparatorWidth = 0;

    public BoxGridLayout(Context context) {
        this(context, null);
    }

    public BoxGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoxGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoxGridLayout, 0, defStyle);

        mSeparatorWidth = a.getDimensionPixelSize(R.styleable.BoxGridLayout_separatorWidth, 0);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize, heightSize;

        //Get the width based on the measure specs
        widthSize = getDefaultSize(0, widthMeasureSpec);

        //Get the height based on measure specs
        heightSize = getDefaultSize(0, heightMeasureSpec);

        int majorDimension = Math.min(widthSize, heightSize);
        //Measure all child views
        int blockDimension = (majorDimension - (COUNT - 1) * mSeparatorWidth) / COUNT;
        int blockSpec = MeasureSpec.makeMeasureSpec(blockDimension, MeasureSpec.EXACTLY);
        measureChildren(blockSpec, blockSpec);

        //MUST call this to save our own dimensions
        setMeasuredDimension(majorDimension, majorDimension);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row, col, left, top;
        for (int i = 0; i < getChildCount(); i++) {
            row = i / COUNT;
            col = i % COUNT;
            View child = getChildAt(i);
            if (col == 0) {
                left = col * child.getMeasuredWidth();
            } else {
                left = col * child.getMeasuredWidth() + mSeparatorWidth * col;
            }

            if (row == 0) {
                top = row * child.getMeasuredHeight();
            } else {
                top = row * child.getMeasuredHeight() + mSeparatorWidth * row;
            }

            child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new BoxGridLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new BoxGridLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public void addView(View child) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (getChildCount() > 8) {
            throw new IllegalStateException("BoxGridLayout cannot have more than 9 direct children");
        }

        super.addView(child, width, height);
    }
}
