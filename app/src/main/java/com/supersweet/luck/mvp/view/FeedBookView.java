package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;

public interface FeedBookView extends BaseView {

    //上传图片
    void success(Object data);

    //错误提示
    void errorShake( String msg);
}
