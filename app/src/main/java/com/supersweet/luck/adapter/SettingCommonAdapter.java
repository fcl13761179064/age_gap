package com.supersweet.luck.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;

public class SettingCommonAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public SettingCommonAdapter() {
        super(R.layout.setting_common_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView view = helper.getView(R.id.tv_des);
        view.setText(item);

    }
}
