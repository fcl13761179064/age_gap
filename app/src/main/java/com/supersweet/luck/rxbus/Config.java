package com.supersweet.luck.rxbus;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/01/25
 *     desc  :
 * </pre>
 */
public class Config {


    private static Myinfo myinfo;

    public static void restoreMsg(Myinfo myinfo) {
        myinfo = myinfo;
    }

    public static Myinfo appendMsg() {
        if (myinfo != null) {
            myinfo = myinfo;
        }
        return myinfo;
    }


}
