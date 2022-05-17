package com.supersweet.luck.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.supersweet.luck.R;
import com.supersweet.luck.base.BasicActivity;
import com.supersweet.luck.wheelview.adapter.SimpleWheelAdapter;
import com.supersweet.luck.wheelview.common.WheelData;
import com.supersweet.luck.wheelview.util.WheelUtils;
import com.supersweet.luck.wheelview.widget.WheelView;
import com.supersweet.luck.widget.AppBar;
import com.supersweet.luck.widget.AppData;

import java.util.ArrayList;

import butterknife.BindView;

public class ChooseAgeActivity extends BasicActivity {

    @BindView(R.id.wheel_view)
    WheelView simpleWheelView;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_age;
    }

    @Override
    protected void initView() {
        ArrayList<WheelData> datas = createDatas();
        simpleWheelView.setWheelAdapter(new SimpleWheelAdapter(this));
        simpleWheelView.setWheelSize(9);
        simpleWheelView.setWheelData(datas);
        simpleWheelView.setLoop(true);
        simpleWheelView.setSelection(0);
        simpleWheelView.setSkin(WheelView.Skin.Holo);
        simpleWheelView.setWheelClickable(true);
        WheelView.WheelViewStyle style1 = new WheelView.WheelViewStyle();
        style1.holoBorderColor =R.color.age_device;
        style1.selectedTextSize =24;
        style1.textSize =20;
        simpleWheelView.setStyle(style1);
        simpleWheelView.setOnWheelItemClickListener((position, o) ->
                WheelUtils.log("click:" + position));
        simpleWheelView.setOnWheelItemSelectedListener((WheelView.OnWheelItemSelectedListener<WheelData>) (position, data) ->
                AppData.age =data.getName()
        );
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
                Intent intent = new Intent(ChooseAgeActivity.this, CurrentLocationActivity.class);
                intent.putExtras(getIntent());
                startActivity(intent);
            }
        });
    }

    private ArrayList<WheelData> createDatas() {
        ArrayList<WheelData> list = new ArrayList<>();
        WheelData item;
        for (int i = 18; i <= 88; i++) {
            item = new WheelData();
            item.setName( i+"");
            list.add(item);
        }
        return list;
    }
}
