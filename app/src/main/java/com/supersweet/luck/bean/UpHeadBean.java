package com.supersweet.luck.bean;

import java.io.Serializable;

public class UpHeadBean  implements Serializable {


    /**
     * msg : /file/avatar/1606654712217-1606654706561.jpg
     * code : 0
     */

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
