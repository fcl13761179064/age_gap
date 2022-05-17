package com.supersweet.luck.bean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 所有的model必须继承此类
 */
public class BaseResult<T> implements Serializable {
    @SerializedName("code")
    public String code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public T data;

    public boolean isSuccess() {
        if (code.equals("0")) {
            return true;
        } else{
            return false;
        }
    }

    public boolean needLogin() {
        return code.equals("5001");
    }
}
