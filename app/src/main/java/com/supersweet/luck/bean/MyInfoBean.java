package com.supersweet.luck.bean;

import java.io.Serializable;

public class MyInfoBean  implements Serializable {

    /**
     * msg : success
     * code : 0
     * user : {"userId":1,"userName":"999999999","account":"999999999","email":"111111@qq.com","sex":"1","age":18,"country":"China Sichuan Chengdu","city":null,"station":null,"height":null,"body":"Athletic","hair":"Auburn","userStatus":"Single","education":"Athletic","ethnicity":null,"drinking":"Non drinker","smoking":"Non smoker","children":"No child","about":"我饿爷爷朩无咯我觉着也我也我用心用完后哟哟哟喂哦我依我看就是这样6 46969469694869846985695698698569856989685698989898696696969699696698968569846996586958646908689496849866984696658","avatar":"b","createTime":"2020-10-11 15:20:42","updateTime":"2020-10-11 15:20:42","yn":1,"isShow":1,"leaveReason":null,"verifyStatus":-1,"verifyPhoto":null,"albumNum":0,"updateFlag":-1,"updateAbout":"我饿爷爷朩无咯我觉着也我也我用心用完后哟哟哟喂哦我依我看就是这样6 46969469694869846985695698698569856989685698989898696696969699696698968569846996586958646908689496849866984696658","updateAvatar":"/storage/emulated/0/Android/data/com.supersweet.luck/files/Pictures/1602400821766.jpg","deviceId":null,"regSource":2,"vagueLevel":1,"userCoin":0,"messageFreeFlag":null,"messageUseCoin":null,"matchFreeFlag":null,"matchUseCoin":null,"interestMeFreeFlag":null,"interestMeUseCoin":null,"videoMinuteUseCoin":null,"highLightFlag":null,"expireTimeSeconds":null}
     */

    private String msg;
    private int code;
    private UserBean user;

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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean implements Serializable{
        /**
         * userId : 1
         * userName : 999999999
         * account : 999999999
         * email : 111111@qq.com
         * sex : 1
         * age : 18
         * country : China Sichuan Chengdu
         * city : null
         * station : null
         * height : null
         * body : Athletic
         * hair : Auburn
         * userStatus : Single
         * education : Athletic
         * ethnicity : null
         * drinking : Non drinker
         * smoking : Non smoker
         * children : No child
         * about : 我饿爷爷朩无咯我觉着也我也我用心用完后哟哟哟喂哦我依我看就是这样6 46969469694869846985695698698569856989685698989898696696969699696698968569846996586958646908689496849866984696658
         * avatar : b
         * createTime : 2020-10-11 15:20:42
         * updateTime : 2020-10-11 15:20:42
         * yn : 1
         * isShow : 1
         * leaveReason : null
         * verifyStatus : -1
         * verifyPhoto : null
         * albumNum : 0
         * updateFlag : -1
         * updateAbout : 我饿爷爷朩无咯我觉着也我也我用心用完后哟哟哟喂哦我依我看就是这样6 46969469694869846985695698698569856989685698989898696696969699696698968569846996586958646908689496849866984696658
         * updateAvatar : /storage/emulated/0/Android/data/com.supersweet.luck/files/Pictures/1602400821766.jpg
         * deviceId : null
         * regSource : 2
         * vagueLevel : 1
         * userCoin : 0
         * messageFreeFlag : null
         * messageUseCoin : null
         * matchFreeFlag : null
         * matchUseCoin : null
         * interestMeFreeFlag : null
         * interestMeUseCoin : null
         * videoMinuteUseCoin : null
         * highLightFlag : null
         * expireTimeSeconds : null
         */

        private int userId;
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
        private String createTime;
        private String updateTime;
        private int yn;
        private int isShow;
        private String leaveReason;
        private int verifyStatus;
        private String verifyPhoto;
        private int albumNum;
        private int updateFlag;
        private String updateAbout;
        private String updateAvatar;
        private String deviceId;
        private int regSource;
        private int vagueLevel;
        private int userCoin;
        private String messageFreeFlag;
        private String messageUseCoin;
        private String matchFreeFlag;
        private String matchUseCoin;
        private String interestMeFreeFlag;
        private String interestMeUseCoin;
        private String videoMinuteUseCoin;
        private String highLightFlag;
        private long expireTimeSeconds;
        private String ifEachOther;
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

        public String getIfEachOther() {
            return ifEachOther;
        }

        public void setIfEachOther(String ifEachOther) {
            this.ifEachOther = ifEachOther;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getYn() {
            return yn;
        }

        public void setYn(int yn) {
            this.yn = yn;
        }

        public int getIsShow() {
            return isShow;
        }

        public void setIsShow(int isShow) {
            this.isShow = isShow;
        }

        public String getLeaveReason() {
            return leaveReason;
        }

        public void setLeaveReason(String leaveReason) {
            this.leaveReason = leaveReason;
        }

        public int getVerifyStatus() {
            return verifyStatus;
        }

        public void setVerifyStatus(int verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public String getVerifyPhoto() {
            return verifyPhoto;
        }

        public void setVerifyPhoto(String verifyPhoto) {
            this.verifyPhoto = verifyPhoto;
        }

        public int getAlbumNum() {
            return albumNum;
        }

        public void setAlbumNum(int albumNum) {
            this.albumNum = albumNum;
        }

        public int getUpdateFlag() {
            return updateFlag;
        }

        public void setUpdateFlag(int updateFlag) {
            this.updateFlag = updateFlag;
        }

        public String getUpdateAbout() {
            return updateAbout;
        }

        public void setUpdateAbout(String updateAbout) {
            this.updateAbout = updateAbout;
        }

        public String getUpdateAvatar() {
            return updateAvatar;
        }

        public void setUpdateAvatar(String updateAvatar) {
            this.updateAvatar = updateAvatar;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public int getRegSource() {
            return regSource;
        }

        public void setRegSource(int regSource) {
            this.regSource = regSource;
        }

        public int getVagueLevel() {
            return vagueLevel;
        }

        public void setVagueLevel(int vagueLevel) {
            this.vagueLevel = vagueLevel;
        }

        public int getUserCoin() {
            return userCoin;
        }

        public void setUserCoin(int userCoin) {
            this.userCoin = userCoin;
        }

        public String getMessageFreeFlag() {
            return messageFreeFlag;
        }

        public void setMessageFreeFlag(String messageFreeFlag) {
            this.messageFreeFlag = messageFreeFlag;
        }

        public String getMessageUseCoin() {
            return messageUseCoin;
        }

        public void setMessageUseCoin(String messageUseCoin) {
            this.messageUseCoin = messageUseCoin;
        }

        public String getMatchFreeFlag() {
            return matchFreeFlag;
        }

        public void setMatchFreeFlag(String matchFreeFlag) {
            this.matchFreeFlag = matchFreeFlag;
        }

        public String getMatchUseCoin() {
            return matchUseCoin;
        }

        public void setMatchUseCoin(String matchUseCoin) {
            this.matchUseCoin = matchUseCoin;
        }

        public String getInterestMeFreeFlag() {
            return interestMeFreeFlag;
        }

        public void setInterestMeFreeFlag(String interestMeFreeFlag) {
            this.interestMeFreeFlag = interestMeFreeFlag;
        }

        public String getInterestMeUseCoin() {
            return interestMeUseCoin;
        }

        public void setInterestMeUseCoin(String interestMeUseCoin) {
            this.interestMeUseCoin = interestMeUseCoin;
        }

        public String getVideoMinuteUseCoin() {
            return videoMinuteUseCoin;
        }

        public void setVideoMinuteUseCoin(String videoMinuteUseCoin) {
            this.videoMinuteUseCoin = videoMinuteUseCoin;
        }

        public String getHighLightFlag() {
            return highLightFlag;
        }

        public void setHighLightFlag(String highLightFlag) {
            this.highLightFlag = highLightFlag;
        }

        public long getExpireTimeSeconds() {
            return expireTimeSeconds;
        }

        public void setExpireTimeSeconds(long expireTimeSeconds) {
            this.expireTimeSeconds = expireTimeSeconds;
        }
    }
}
