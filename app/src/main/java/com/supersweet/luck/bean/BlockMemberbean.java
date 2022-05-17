package com.supersweet.luck.bean;

import java.io.Serializable;
import java.util.List;


public class BlockMemberbean implements Serializable {

    private Integer totalCount;
    private Integer pageSize;
    private Integer totalPage;
    private Integer currPage;
    private List<BlickMemberInfo>  list;

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

    public List<BlickMemberInfo> getList() {
        return list;
    }

    public void setList(List<BlickMemberInfo> list) {
        this.list = list;
    }

    public static class  BlickMemberInfo implements Serializable{
        private String userName;
        private String account;
        private String email;
        private int userId;
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
        private String createTime;
        private String updateTime;
        private Integer yn;
        private Integer isShow;
        private Object leaveReason;
        private Integer verifyStatus;
        private Object verifyPhoto;
        private Integer albumNum;
        private Integer updateFlag;
        private String updateAbout;
        private String updateAvatar;
        private String deviceId;
        private Integer regSource;
        private Integer vagueLevel;
        private Integer userCoin;
        private Object messageFreeFlag;
        private Object messageUseCoin;
        private Object matchFreeFlag;
        private Object matchUseCoin;
        private Object interestMeFreeFlag;
        private Object interestMeUseCoin;
        private Object videoMinuteUseCoin;
        private Object highLightFlag;
        private Object expireTimeSeconds;

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

        public Integer getYn() {
            return yn;
        }

        public void setYn(Integer yn) {
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

        public Integer getUpdateFlag() {
            return updateFlag;
        }

        public void setUpdateFlag(Integer updateFlag) {
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

        public Object getHighLightFlag() {
            return highLightFlag;
        }

        public void setHighLightFlag(Object highLightFlag) {
            this.highLightFlag = highLightFlag;
        }

        public Object getExpireTimeSeconds() {
            return expireTimeSeconds;
        }

        public void setExpireTimeSeconds(Object expireTimeSeconds) {
            this.expireTimeSeconds = expireTimeSeconds;
        }
    }

}
