package com.supersweet.luck.rxbus;

import java.io.Serializable;

public  class Myinfo implements Serializable {

    private  String chooseSex;
    private  String minAge;
    private  String maxAge;
    private  String chooseCountryCode;

    public String getChooseCountryCode() {
        return chooseCountryCode;
    }

    public void setChooseCountryCode(String chooseCountryCode) {
        this.chooseCountryCode = chooseCountryCode;
    }

    public String getChooseSex() {
        return chooseSex;
    }

    public void setChooseSex(String chooseSex) {
        this.chooseSex = chooseSex;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }
}
