package com.supersweet.luck.bean;

/**
 * @描述
 * @作者 fanchunlei
 * @时间 2020/6/3
 */
public class User {
    private String code;
    private String expire;//过期
    private String token;
    private String msg;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
