package com.supersweet.luck.myphoto;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;


public class BrowseDecoration extends RecyclerView.ItemDecoration {

    private int mPadding;

    public BrowseDecoration(Context context) {
        mPadding =context.getResources().getDimensionPixelSize(R.dimen.album_divide);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mPadding, mPadding, mPadding, mPadding);
    }
}