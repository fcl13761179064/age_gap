package com.supersweet.luck.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;

/**
 * @描述 adapter
 * @作者 fanchunlei
 * @时间 2017/8/7
 */
public class CommonAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private ItemSelectedCallBack mCallBack;

    public CommonAdapter() {
        super(R.layout.adapter_body_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, String title) {
        helper.setText(R.id.tv_body_type, title);
        if (mCallBack != null) {
            mCallBack.convert(helper, helper.getLayoutPosition(),title);
        }
    }

    public void setItemSelectedCallBack(ItemSelectedCallBack CallBack) {
        this.mCallBack = CallBack;
    }

    public interface ItemSelectedCallBack {
        void convert(BaseViewHolder holder, int position,String title);
    }
}
