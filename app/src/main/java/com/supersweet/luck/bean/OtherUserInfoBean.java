package com.supersweet.luck.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class OtherUserInfoBean implements Serializable {


    /**
     * userId : 23
     * userName : 99999j
     * account : 99999j
     * email : gg@163.com
     * sex : Male
     * age : 66
     * country : China
     * city : Sichuan
     * station : Chengdu
     * height : 161cm
     * body : Athletic
     * hair : Auburn
     * userStatus : Single
     * education : High school
     * ethnicity : Arabic/Middle easter
     * drinking : Non drinker
     * smoking : Non smoker
     * children : No child
     * about : 我婆婆公公你问我爱你有多深'在家没''我在吃饭哦哦哦哦哦哦哦哦哦哦哦好吧！哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦哦
     * avatar : /file/avatar/1612017552918-1612017527339_20210130223847.jpg
     * ifConnection : 1
     * ifBlock : -1
     * isShow : 1
     * isVerify : -1
     * ifEachOther : 2
     * isOnline : -1
     * albumNum : 0
     * verifyPhoto : null
     * updateFlag : 1
     * vagueLevel : 1
     * messageFreeFlag : -1
     * messageUseCoin : 5
     * matchFreeFlag : -1
     * matchUseCoin : 3
     * interestMeFreeFlag : -1
     * interestMeUseCoin : 3
     * videoMinuteUseCoin : 5
     * userCoin : 0
     * highLightFlag : -1
     * expireTimeSeconds : 0
     * highLightUseCoin : 10
     */

    private String userId;
    private String userName;
    private String account;
    private String email;
    private String sex;
    private int age;
    private String country;
    private String city;
    private String station;
    private String height;
    private String body;
    private String hair;
    private String userStatus;
    private String education;
    private String ethnicity;
    private String drinking;
    private String smoking;
    private String children;
    private String about;
    private String avatar;
    private int ifConnection;
    private int ifBlock;
    private int isShow;
    private int isVerify;
    private int ifEachOther;
    private int isOnline;
    private int albumNum;
    private Object verifyPhoto;
    private int updateFlag;
    private int vagueLevel;
    private int messageFreeFlag;
    private int messageUseCoin;
    private int matchFreeFlag;
    private int matchUseCoin;
    private int interestMeFreeFlag;
    private int interestMeUseCoin;
    private int videoMinuteUseCoin;
    private int userCoin;
    private int highLightFlag;
    private int expireTimeSeconds;
    private int highLightUseCoin;
    private String Message;
    private String qscore;
    private int monthFlag;

    public int getMonthFlag() {
        return monthFlag;
    }

    public void setMonthFlag(int monthFlag) {
        this.monthFlag = monthFlag;
    }

    public String getQscore() {
        return qscore;
    }

    public void setQscore(String qscore) {
        this.qscore = qscore;
    }
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
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

    public int getIfConnection() {
        return ifConnection;
    }

    public void setIfConnection(int ifConnection) {
        this.ifConnection = ifConnection;
    }

    public int getIfBlock() {
        return ifBlock;
    }

    public void setIfBlock(int ifBlock) {
        this.ifBlock = ifBlock;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public int getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(int isVerify) {
        this.isVerify = isVerify;
    }

    public int getIfEachOther() {
        return ifEachOther;
    }

    public void setIfEachOther(int ifEachOther) {
        this.ifEachOther = ifEachOther;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public int getAlbumNum() {
        return albumNum;
    }

    public void setAlbumNum(int albumNum) {
        this.albumNum = albumNum;
    }

    public Object getVerifyPhoto() {
        return verifyPhoto;
    }

    public void setVerifyPhoto(Object verifyPhoto) {
        this.verifyPhoto = verifyPhoto;
    }

    public int getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(int updateFlag) {
        this.updateFlag = updateFlag;
    }

    public int getVagueLevel() {
        return vagueLevel;
    }

    public void setVagueLevel(int vagueLevel) {
        this.vagueLevel = vagueLevel;
    }

    public int getMessageFreeFlag() {
        return messageFreeFlag;
    }

    public void setMessageFreeFlag(int messageFreeFlag) {
        this.messageFreeFlag = messageFreeFlag;
    }

    public int getMessageUseCoin() {
        return messageUseCoin;
    }

    public void setMessageUseCoin(int messageUseCoin) {
        this.messageUseCoin = messageUseCoin;
    }

    public int getMatchFreeFlag() {
        return matchFreeFlag;
    }

    public void setMatchFreeFlag(int matchFreeFlag) {
        this.matchFreeFlag = matchFreeFlag;
    }

    public int getMatchUseCoin() {
        return matchUseCoin;
    }

    public void setMatchUseCoin(int matchUseCoin) {
        this.matchUseCoin = matchUseCoin;
    }

    public int getInterestMeFreeFlag() {
        return interestMeFreeFlag;
    }

    public void setInterestMeFreeFlag(int interestMeFreeFlag) {
        this.interestMeFreeFlag = interestMeFreeFlag;
    }

    public int getInterestMeUseCoin() {
        return interestMeUseCoin;
    }

    public void setInterestMeUseCoin(int interestMeUseCoin) {
        this.interestMeUseCoin = interestMeUseCoin;
    }

    public int getVideoMinuteUseCoin() {
        return videoMinuteUseCoin;
    }

    public void setVideoMinuteUseCoin(int videoMinuteUseCoin) {
        this.videoMinuteUseCoin = videoMinuteUseCoin;
    }

    public int getUserCoin() {
        return userCoin;
    }

    public void setUserCoin(int userCoin) {
        this.userCoin = userCoin;
    }

    public int getHighLightFlag() {
        return highLightFlag;
    }

    public void setHighLightFlag(int highLightFlag) {
        this.highLightFlag = highLightFlag;
    }

    public int getExpireTimeSeconds() {
        return expireTimeSeconds;
    }

    public void setExpireTimeSeconds(int expireTimeSeconds) {
        this.expireTimeSeconds = expireTimeSeconds;
    }

    public int getHighLightUseCoin() {
        return highLightUseCoin;
    }

    public void setHighLightUseCoin(int highLightUseCoin) {
        this.highLightUseCoin = highLightUseCoin;
    }
}
