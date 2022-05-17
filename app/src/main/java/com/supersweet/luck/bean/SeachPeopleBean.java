package com.supersweet.luck.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SeachPeopleBean {


    /* userId : 8
     * userName : fcl111
     * account : fcl111
     * email : 14@qq.com
     * sex : Female
     * age : 18
     * country : Zimbabwe
     * city : Sichuan
     * station : Chengdu
     * height : 100cm
     * body : Athletic
     * hair : Auburn
     * userStatus : Single
     * education : High school
     * ethnicity : Arabic/Middle easter
     * drinking : Non drinker
     * smoking : Non smoker
     * children : No child
     * about : bbzbsbdndndndndndfnfnfnfnfjfnfnfdnfnfnfnfnfndndfnfnfndndndndndnfndnfnfndndnfnfndnxnxnxnfnxnxnxnxnxnxnxxnxncnnxnxnxxnxncn cnxnnccnnccnncncncncncncncncncncncncfjfjfjjffjfjfjfj
     * avatar : /file/avatar/1605255662480-1605255661447.jpg
     * ifConnection : -1
     * ifBlock : -1
     * isShow : 1
     * isVerify : -1
     * ifEachOther : -1
     * isOnline : -1
     * albumNum : 0
     * verifyPhoto : null
     * updateFlag : 1
     * vagueLevel : 1
     * messageFreeFlag : null
     * messageUseCoin : null
     * matchFreeFlag : null
     * matchUseCoin : null
     * interestMeFreeFlag : null
     * interestMeUseCoin : null
     * videoMinuteUseCoin : null
     * userCoin : 0
     * highLightFlag : -1
     * expireTimeSeconds : 0
     * highLightUseCoin : null
     */

    @SerializedName("userId")
    private int userId;
    @SerializedName("userName")
    private String userName;
    @SerializedName("account")
    private String account;
    @SerializedName("email")
    private String email;
    @SerializedName("sex")
    private String sex;
    @SerializedName("age")
    private Integer age;
    @SerializedName("country")
    private String country;
    @SerializedName("city")
    private String city;
    @SerializedName("station")
    private String station;
    @SerializedName("height")
    private String height;
    @SerializedName("body")
    private String body;
    @SerializedName("hair")
    private String hair;
    @SerializedName("userStatus")
    private String userStatus;
    @SerializedName("education")
    private String education;
    @SerializedName("ethnicity")
    private String ethnicity;
    @SerializedName("drinking")
    private String drinking;
    @SerializedName("smoking")
    private String smoking;
    @SerializedName("children")
    private String children;
    @SerializedName("about")
    private String about;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("ifConnection")
    private Integer ifConnection;
    @SerializedName("ifBlock")
    private Integer ifBlock;
    @SerializedName("isShow")
    private Integer isShow;
    @SerializedName("isVerify")
    private Integer isVerify;
    @SerializedName("ifEachOther")
    private Integer ifEachOther;
    @SerializedName("isOnline")
    private int isOnline;
    @SerializedName("albumNum")
    private Integer albumNum;
    @SerializedName("verifyPhoto")
    private String verifyPhoto;
    @SerializedName("updateFlag")
    private Integer updateFlag;
    @SerializedName("vagueLevel")
    private Integer vagueLevel;
    @SerializedName("messageFreeFlag")
    private Object messageFreeFlag;
    @SerializedName("messageUseCoin")
    private Object messageUseCoin;
    @SerializedName("matchFreeFlag")
    private Object matchFreeFlag;
    @SerializedName("matchUseCoin")
    private Object matchUseCoin;
    @SerializedName("interestMeFreeFlag")
    private Object interestMeFreeFlag;
    @SerializedName("interestMeUseCoin")
    private Object interestMeUseCoin;
    @SerializedName("videoMinuteUseCoin")
    private Object videoMinuteUseCoin;
    @SerializedName("userCoin")
    private Integer userCoin;
    @SerializedName("highLightFlag")
    private Integer highLightFlag;
    @SerializedName("expireTimeSeconds")
    private Integer expireTimeSeconds;
    @SerializedName("highLightUseCoin")
    private Object highLightUseCoin;
    @SerializedName("qscore")
    private String qscore;

    public String getQscore() {
        return qscore;
    }

    public void setQscore(String qscore) {
        this.qscore = qscore;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIfConnection() {
        return ifConnection;
    }

    public void setIfConnection(Integer ifConnection) {
        this.ifConnection = ifConnection;
    }

    public Integer getIfBlock() {
        return ifBlock;
    }

    public void setIfBlock(Integer ifBlock) {
        this.ifBlock = ifBlock;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(Integer isVerify) {
        this.isVerify = isVerify;
    }

    public Integer getIfEachOther() {
        return ifEachOther;
    }

    public void setIfEachOther(Integer ifEachOther) {
        this.ifEachOther = ifEachOther;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getAlbumNum() {
        return albumNum;
    }

    public void setAlbumNum(Integer albumNum) {
        this.albumNum = albumNum;
    }

    public String getVerifyPhoto() {
        return verifyPhoto;
    }

    public void setVerifyPhoto(String verifyPhoto) {
        this.verifyPhoto = verifyPhoto;
    }

    public Integer getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(Integer updateFlag) {
        this.updateFlag = updateFlag;
    }

    public Integer getVagueLevel() {
        return vagueLevel;
    }

    public void setVagueLevel(Integer vagueLevel) {
        this.vagueLevel = vagueLevel;
    }

    public Object getMessageFreeFlag() {
        return messageFreeFlag;
    }

    public void setMessageFreeFlag(Object messageFreeFlag) {
        this.messageFreeFlag = messageFreeFlag;
    }

    public Object getMessageUseCoin() {
        return messageUseCoin;
    }

    public void setMessageUseCoin(Object messageUseCoin) {
        this.messageUseCoin = messageUseCoin;
    }

    public Object getMatchFreeFlag() {
        return matchFreeFlag;
    }

    public void setMatchFreeFlag(Object matchFreeFlag) {
        this.matchFreeFlag = matchFreeFlag;
    }

    public Object getMatchUseCoin() {
        return matchUseCoin;
    }

    public void setMatchUseCoin(Object matchUseCoin) {
        this.matchUseCoin = matchUseCoin;
    }

    public Object getInterestMeFreeFlag() {
        return interestMeFreeFlag;
    }

    public void setInterestMeFreeFlag(Object interestMeFreeFlag) {
        this.interestMeFreeFlag = interestMeFreeFlag;
    }

    public Object getInterestMeUseCoin() {
        return interestMeUseCoin;
    }

    public void setInterestMeUseCoin(Object interestMeUseCoin) {
        this.interestMeUseCoin = interestMeUseCoin;
    }

    public Object getVideoMinuteUseCoin() {
        return videoMinuteUseCoin;
    }

    public void setVideoMinuteUseCoin(Object videoMinuteUseCoin) {
        this.videoMinuteUseCoin = videoMinuteUseCoin;
    }

    public Integer getUserCoin() {
        return userCoin;
    }

    public void setUserCoin(Integer userCoin) {
        this.userCoin = userCoin;
    }

    public Integer getHighLightFlag() {
        return highLightFlag;
    }

    public void setHighLightFlag(Integer highLightFlag) {
        this.highLightFlag = highLightFlag;
    }

    public Integer getExpireTimeSeconds() {
        return expireTimeSeconds;
    }

    public void setExpireTimeSeconds(Integer expireTimeSeconds) {
        this.expireTimeSeconds = expireTimeSeconds;
    }

    public Object getHighLightUseCoin() {
        return highLightUseCoin;
    }

    public void setHighLightUseCoin(Object highLightUseCoin) {
        this.highLightUseCoin = highLightUseCoin;
    }
}
