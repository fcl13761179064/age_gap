package com.supersweet.luck.bean;



public class SelfAlbumBean {

    /**
     * id : 1
     * userId : 8
     * photoPath : /file/album/1605606469371-mmexport1595064804718.jpg
     * reviewStatus : 2
     * yn : 1
     * createTime : 2020-11-16T20:47:49.000+0000
     * updateTime : 2020-11-16T20:47:49.000+0000
     */

    private Integer id;
    private Integer userId;
    private String photoPath;
    private Integer reviewStatus;
    private Integer yn;
    private String createTime;
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Integer getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
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
}
