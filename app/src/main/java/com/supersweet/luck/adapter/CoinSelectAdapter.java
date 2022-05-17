package com.supersweet.luck.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.ui.MainActivity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @描述 adapter
 * @作者 fanchunlei
 * @时间 2017/8/7
 */
public class CoinSelectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private ItemSelectedCallBack mCallBack;

    public CoinSelectAdapter() {
        super(R.layout.adapter_coins_type);
    }

    @Override
    protected void convert(BaseViewHolder helper, String data) {
        String[] split = data.split(",");
        String  key = split[0];
        String  value = split[1];
            helper.setText(R.id.tv_num_coin, key);
            helper.setText(R.id.tv_hundry_coin, value);
        if (mCallBack != null) {
            mCallBack.convert(helper, helper.getLayoutPosition(),data);
        }
    }

    public void setItemSelectedCallBack(ItemSelectedCallBack CallBack) {
        this.mCallBack = CallBack;
    }


    public interface ItemSelectedCallBack {
        void convert(BaseViewHolder holder, int position,String title);
    }
}
