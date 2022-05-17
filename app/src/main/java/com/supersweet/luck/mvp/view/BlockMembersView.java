package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.BlockMemberbean;

import java.util.List;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface BlockMembersView extends BaseView {

    //错误提示
    void errorShake(String msg);

    void BlockMemberSuccess(BlockMemberbean data);

    void unBlockMemberSuccess(Object data);

}
