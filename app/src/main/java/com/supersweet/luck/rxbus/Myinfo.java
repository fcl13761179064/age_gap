package com.supersweet.luck.rxbus;


import java.io.Serializable;

public  class Myinfo implements Serializable {

    private  String chooseSex;
    private  String minAge;
    private  String maxAge;
    private  String chooseCountryCode;
    private  String height;
    private  String body;
    private  String hair;
    private  String relationship;
    private  String education;
    private  String ethnicity;
    private  String drinking;
    private  String smoking;
    private  String children;
    private  String disatance;

    public String getDisatance() {
        return disatance;
    }

    public void setDisatance(String disatance) {
        this.disatance = disatance;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHair() {
        return hair;
    }

    public void setHair(String hair) {
        this.hair = hair;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getDrinking() {
        return drinking;
    }

    public void setDrinking(String drinking) {
        this.drinking = drinking;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

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
