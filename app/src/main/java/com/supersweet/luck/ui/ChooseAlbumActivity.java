package com.supersweet.luck.ui;

import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.supersweet.luck.R;
import com.supersweet.luck.adapter.AlbumAdapter;
import com.supersweet.luck.base.BasicActivity;
import com.supersweet.luck.myphoto.AlbumPicker;
import com.supersweet.luck.myphoto.BrowseDecoration;

import butterknife.BindView;

public class ChooseAlbumActivity extends BasicActivity {
    @BindView(R.id.iv_album_back)
    LinearLayout iv_album_back;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    public static AlbumPicker albumPicker;
    private static final int SPAN_COUNT = 4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_album;
    }

    @Override
    protected void initView() {
        setBucket(0);

    }

    @Override
    protected void initListener() {

    }

    public void setBucket(int bucketIndex) {
        albumPicker = new AlbumPicker(ChooseAlbumActivity.this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, SPAN_COUNT));
        mRecyclerView.addItemDecoration(new BrowseDecoration(this));
        mRecyclerView.setAdapter(new AlbumAdapter(ChooseAlbumActivity.this, bucketIndex));

    }

}
