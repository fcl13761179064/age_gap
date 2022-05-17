package com.supersweet.luck.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supersweet.luck.R;
import com.supersweet.luck.base.BasicActivity;

import butterknife.BindView;

public class TakePhotoActivity extends BasicActivity {

    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_take_photo)
    ImageView iv_take_photo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_avatha;
    }

    @Override
    protected void initView() {
        iv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TakePhotoActivity.this, AbountActivity.class);
                startActivity(intent);
            }
        });
    }
}
