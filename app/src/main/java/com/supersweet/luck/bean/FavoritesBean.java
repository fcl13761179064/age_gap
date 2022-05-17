package com.supersweet.luck.bean;

import com.google.gson.annotations.SerializedName;
import com.supersweet.luck.widget.MyDatas;

import java.io.Serializable;
import java.util.List;


public class FavoritesBean implements Serializable {
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

    public static class Love implements Serializable {


        /**
         * userId : 6
         * userName : fcl007
         * account : fcl007
         * email : 32034@qq.com
         * sex : 1
         * age : 18
         * country : Zimbabwe
         * city : Sichuan
         * station : Chengdu
         * height : 100
         * body : 001
         * hair : 002
         * userStatus : 003
         * education : 004
         * ethnicity : 005
         * drinking : 004
         * smoking : 003
         * children : 002
         * about : 庸人自扰之可以可以陌陌娱乐无极限加油加油加油no莫咯ing默默哦咯莫明末哦哦哦你谎言西西里就一句话咦哟哟哟我永远哟哟1宗健身57575456老师
         * avatar : /file/avatar/1605084057461-1605084056544.jpg
         * createTime : null
         * updateTime : null
         * yn : null
         * isShow : 1
         * leaveReason : null
         * verifyStatus : -1
         * verifyPhoto : null
         * albumNum : 0
         * updateFlag : null
         * updateAbout : null
         * updateAvatar : null
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
         * highLightFlag : -1
         * expireTimeSeconds : 0
         * connectionId : null
         * isOnline : -1
         * ifConnection : -1
         */

        private int userId;
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
        private Object messageFreeFlag;
        private int messageUseCoin;
        private int matchFreeFlag;
        private int matchUseCoin;
        private int interestMeFreeFlag;
        private int interestMeUseCoin;
        private Object videoMinuteUseCoin;
        private int highLightFlag;
        private Integer expireTimeSeconds;
        private Object connectionId;
        private int isOnline;
        private Integer ifConnection;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
            MyDatas.bodyTypes();
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

        public Object getMessageFreeFlag() {
            return messageFreeFlag;
        }

        public void setMessageFreeFlag(Object messageFreeFlag) {
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

        public Object getVideoMinuteUseCoin() {
            return videoMinuteUseCoin;
        }

        public void setVideoMinuteUseCoin(Object videoMinuteUseCoin) {
            this.videoMinuteUseCoin = videoMinuteUseCoin;
        }

        public int getHighLightFlag() {
            return highLightFlag;
        }

        public void setHighLightFlag(int highLightFlag) {
            this.highLightFlag = highLightFlag;
        }

        public Integer getExpireTimeSeconds() {
            return expireTimeSeconds;
        }

        public void setExpireTimeSeconds(Integer expireTimeSeconds) {
            this.expireTimeSeconds = expireTimeSeconds;
        }

        public Object getConnectionId() {
            return connectionId;
        }

        public void setConnectionId(Object connectionId) {
            this.connectionId = connectionId;
        }

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public Integer getIfConnection() {
            return ifConnection;
        }

        public void setIfConnection(Integer ifConnection) {
            this.ifConnection = ifConnection;
        }
    }
}
