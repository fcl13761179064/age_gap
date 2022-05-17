package com.supersweet.luck.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class LoveMeBean implements Serializable {
    /**
     * totalCount : 0
     * pageSize : 20
     * totalPage : 0
     * currPage : 1
     * list : []
     */

    private Integer totalCount;
    private Integer pageSize;
    private Integer totalPage;
    private Integer currPage;
    private List<Love> list;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public List<Love> getList() {
        return list;
    }

    public void setList(List<Love> list) {
        this.list = list;
    }

    public class Love implements Serializable {


        /**
         * userId : 8
         * userName : fcl111
         * account : fcl111
         * email : 14@qq.com
         * sex : 2
         * age : 18
         * country : Zimbabwe
         * city : Sichuan
         * station : Chengdu
         * height : 126
         * body : 003
         * hair : 006
         * userStatus : 002
         * education : 001
         * ethnicity : 001
         * drinking : 003
         * smoking : 004
         * children : 006
         * about : next
         * avatar : /file/avatar/1605255662480-1605255661447.jpg
         * createTime : null
         * updateTime : null
         * yn : null
         * isShow : 1
         * leaveReason : null
         * verifyStatus : -1
         * verifyPhoto : null
         * albumNum : 5
         * updateFlag : null
         * updateAbout : null
         * updateAvatar : null
         * deviceId : null
         * regSource : 2
         * vagueLevel : 1
         * userCoin : 0
         * messageFreeFlag : -1
         * messageUseCoin : 5
         * matchFreeFlag : -1
         * matchUseCoin : 3
         * interestMeFreeFlag : -1
         * interestMeUseCoin : 3
         * videoMinuteUseCoin : 5
         * highLightFlag : -1
         * expireTimeSeconds : 0
         */

        private Integer userId;
        private String type;
        private String userName;
        private String account;
        private String email;
        private String sex;
        private Integer age;
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
        private Object createTime;
        private Object updateTime;
        private Object yn;
        private Integer isShow;
        private Object leaveReason;
        private Integer verifyStatus;
        private Object verifyPhoto;
        private Integer albumNum;
        private Object updateFlag;
        private Object updateAbout;
        private Object updateAvatar;
        private Object deviceId;
        private Integer regSource;
        private Integer vagueLevel;
        private Integer userCoin;
        private Integer messageFreeFlag;
        private Integer messageUseCoin;
        private Integer matchFreeFlag;
        private Integer matchUseCoin;
        private Integer interestMeFreeFlag;
        private Integer interestMeUseCoin;
        private Integer videoMinuteUseCoin;
        private Integer highLightFlag;
        private Integer expireTimeSeconds;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getYn() {
            return yn;
        }

        public void setYn(Object yn) {
            this.yn = yn;
        }

        public Integer getIsShow() {
            return isShow;
        }

        public void setIsShow(Integer isShow) {
            this.isShow = isShow;
        }

        public Object getLeaveReason() {
            return leaveReason;
        }

        public void setLeaveReason(Object leaveReason) {
            this.leaveReason = leaveReason;
        }

        public Integer getVerifyStatus() {
            return verifyStatus;
        }

        public void setVerifyStatus(Integer verifyStatus) {
            this.verifyStatus = verifyStatus;
        }

        public Object getVerifyPhoto() {
            return verifyPhoto;
        }

        public void setVerifyPhoto(Object verifyPhoto) {
            this.verifyPhoto = verifyPhoto;
        }

        public Integer getAlbumNum() {
            return albumNum;
        }

        public void setAlbumNum(Integer albumNum) {
            this.albumNum = albumNum;
        }

        public Object getUpdateFlag() {
            return updateFlag;
        }

        public void setUpdateFlag(Object updateFlag) {
            this.updateFlag = updateFlag;
        }

        public Object getUpdateAbout() {
            return updateAbout;
        }

        public void setUpdateAbout(Object updateAbout) {
            this.updateAbout = updateAbout;
        }

        public Object getUpdateAvatar() {
            return updateAvatar;
        }

        public void setUpdateAvatar(Object updateAvatar) {
            this.updateAvatar = updateAvatar;
        }

        public Object getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Object deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getRegSource() {
            return regSource;
        }

        public void setRegSource(Integer regSource) {
            this.regSource = regSource;
        }

        public Integer getVagueLevel() {
            return vagueLevel;
        }

        public void setVagueLevel(Integer vagueLevel) {
            this.vagueLevel = vagueLevel;
        }

        public Integer getUserCoin() {
            return userCoin;
        }

        public void setUserCoin(Integer userCoin) {
            this.userCoin = userCoin;
        }

        public Integer getMessageFreeFlag() {
            return messageFreeFlag;
        }

        public void setMessageFreeFlag(Integer messageFreeFlag) {
            this.messageFreeFlag = messageFreeFlag;
        }

        public Integer getMessageUseCoin() {
            return messageUseCoin;
        }

        public void setMessageUseCoin(Integer messageUseCoin) {
            this.messageUseCoin = messageUseCoin;
        }

        public Integer getMatchFreeFlag() {
            return matchFreeFlag;
        }

        public void setMatchFreeFlag(Integer matchFreeFlag) {
            this.matchFreeFlag = matchFreeFlag;
        }

        public Integer getMatchUseCoin() {
            return matchUseCoin;
        }

        public void setMatchUseCoin(Integer matchUseCoin) {
            this.matchUseCoin = matchUseCoin;
        }

        public Integer getInterestMeFreeFlag() {
            return interestMeFreeFlag;
        }

        public void setInterestMeFreeFlag(Integer interestMeFreeFlag) {
            this.interestMeFreeFlag = interestMeFreeFlag;
        }

        public Integer getInterestMeUseCoin() {
            return interestMeUseCoin;
        }

        public void setInterestMeUseCoin(Integer interestMeUseCoin) {
            this.interestMeUseCoin = interestMeUseCoin;
        }

        public Integer getVideoMinuteUseCoin() {
            return videoMinuteUseCoin;
        }

        public void setVideoMinuteUseCoin(Integer videoMinuteUseCoin) {
            this.videoMinuteUseCoin = videoMinuteUseCoin;
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
    }
}
