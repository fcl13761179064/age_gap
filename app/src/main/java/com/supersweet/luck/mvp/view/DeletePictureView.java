package com.supersweet.luck.mvp.view;

import com.supersweet.luck.base.BaseView;
import com.supersweet.luck.bean.SelfAlbumBean;
import com.supersweet.luck.bean.UpImgBean;

import java.util.List;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2017/8/2
 */
public interface DeletePictureView extends BaseView {

    //错误提示
    void errorShake(int type, int CycleTimes, String msg);

    void Deletemsuccess(List<SelfAlbumBean> data);
}
