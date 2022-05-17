package com.supersweet.luck.adapter;


import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.supersweet.luck.R;
import com.supersweet.luck.bean.FavoritesBean;


/**
 * @描述 自动化 的 adapter
 * @作者 fanchunlei
 * @时间 2017/8/7
 */
public class InterestToMeAdapter extends BaseQuickAdapter<FavoritesBean, BaseViewHolder> {
    private OnEnableChangedListener onEnableChangedListener;

    public InterestToMeAdapter() {
        super(R.layout.favotite_detail_head);
    }

    public void setEnableChangedListener(OnEnableChangedListener onEnableChangedListener) {
        this.onEnableChangedListener = onEnableChangedListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, FavoritesBean ruleEngineBeans) {
     /*   helper.setText(R.id.item_img, ruleEngineBeans.getRuleName());
        helper.addOnClickListener(R.id.tv_edit);
        helper.setOnCheckedChangeListener(R.id.sc_enable, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (null != onEnableChangedListener) {
                    if(buttonView.isPressed()){
                        onEnableChangedListener.onCheckedChanged(buttonView, isChecked, helper.getAdapterPosition() - getHeaderLayoutCount());
                    }
                }
            }
        });*/
    }

    public interface OnEnableChangedListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked, int position);
    }
}
