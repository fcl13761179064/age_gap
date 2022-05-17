/*
 * create by cairurui on 1/24/19 8:43 PM.
 * Copyright (c) 2019 SunseaIoT. All rights reserved.
 */

package com.supersweet.luck.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.supersweet.luck.R;


public class SpecialAppBar extends FrameLayout {
    private final String TAG = this.getClass().getSimpleName();
    private ImageView leftImageView;
    private ImageView rightImageView;
    private TextView leftTextView;
    public TextView rightTextView;
    private LinearLayout leftLinearLayout;
    private LinearLayout rightLinearLayout;
    private View bottom_line;

    public SpecialAppBar(@NonNull Context context) {
        this(context, null);
    }

    public SpecialAppBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpecialAppBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private @DrawableRes
    int left_iv, right_iv;
    private String left_tv, right_tv, center_tv;
    private int right_tv_color;
    private boolean bottom_line_visibility;

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(getContext(), R.layout.common_double_title_appbar_layout, this);

        leftImageView = findViewById(R.id.iv_left);
        leftTextView = findViewById(R.id.tv_left);
        rightImageView = findViewById(R.id.iv_right);
        rightTextView = findViewById(R.id.tv_right);
        leftLinearLayout = findViewById(R.id.left_ll);
        rightLinearLayout = findViewById(R.id.right_ll);
        bottom_line = findViewById(R.id.bottom_line);

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AppBar, defStyleAttr, 0);
        left_iv = ta.getResourceId(R.styleable.AppBar_left_iv, 0);
        left_tv = ta.getString(R.styleable.AppBar_left_tv);
        right_iv = ta.getResourceId(R.styleable.AppBar_right_iv, 0);
        right_tv = ta.getString(R.styleable.AppBar_right_tv);
        center_tv = ta.getString(R.styleable.AppBar_center_tv);
        bottom_line_visibility = ta.getBoolean(R.styleable.AppBar_appbar_bottom_line, true);
        right_tv_color = ta.getColor(R.styleable.AppBar_right_tv_color, ContextCompat.getColor(getContext(), R.color.color_333333));
        ta.recycle();

        adjustContent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        sss();
        if (getChildCount() == 2) {
            final View appBarView = getChildAt(0);
            final View contentView = getChildAt(1);
            final ViewGroup centerContainer = appBarView.findViewById(R.id.appbar_center_container);
            removeView(contentView);
            centerContainer.removeAllViewsInLayout();
            centerContainer.addView(contentView);
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout: ");
        super.onLayout(changed, left, top, right, bottom);
    }

    private void sss() {
        final int leftLLWidth = leftLinearLayout.getMeasuredWidth();
        final int rightLLWidth = rightLinearLayout.getMeasuredWidth();
        final int minWidth = Math.max(leftLLWidth, rightLLWidth);
        leftLinearLayout.setMinimumWidth(minWidth);
        rightLinearLayout.setMinimumWidth(minWidth);
    }

    private void adjustContent() {
        Log.d(TAG, "adjustContent: ");
        if (left_iv == 0) {
            leftImageView.setVisibility(GONE);
        } else {
            leftImageView.setVisibility(VISIBLE);
            leftImageView.setImageResource(left_iv);
        }
        if (TextUtils.isEmpty(left_tv)) {
            leftTextView.setVisibility(GONE);
        } else {
            leftTextView.setVisibility(VISIBLE);
            leftTextView.setText(left_tv);
        }
        if (right_iv == 0) {
            rightImageView.setVisibility(GONE);
        } else {
            rightImageView.setVisibility(VISIBLE);
            rightImageView.setImageResource(right_iv);
        }
        if (TextUtils.isEmpty(right_tv)) {
            rightTextView.setVisibility(GONE);
        } else {
            rightTextView.setVisibility(VISIBLE);
            rightTextView.setText(right_tv);
            rightTextView.setTextColor(right_tv_color);
        }

        if (getBackground() == null) {
            setBackgroundColor(Color.WHITE);
        }
        if (!bottom_line_visibility) {
            bottom_line.setVisibility(GONE);
        }

    }

    public void setLeftText(String text) {
        left_tv = text;
        adjustContent();
    }

    public void setRightText(String text) {
        right_tv = text;
        adjustContent();
    }

    public void setCenterText(String text) {
        center_tv = text;
        adjustContent();
    }

    public void setAppBarlineHider(boolean b) {
        bottom_line_visibility = b;
        adjustContent();
    }

}

