package com.supersweet.luck.bean;

import java.io.Serializable;
import java.util.List;

public class PayCordBean implements Serializable{

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private List<PayCord> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<PayCord> getList() {
        return list;
    }

    public void setList(List<PayCord> list) {
        this.list = list;
    }

    public static class PayCord implements Serializable {
            private int id;
            private int userId;
            private Object targetUserId;
            private int coinType;
            private String coinName;
            private String coinMemo;
            private int changeNum;
            private String eventTime;
            private int yn;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getTargetUserId() {
            return targetUserId;
        }

        public void setTargetUserId(Object targetUserId) {
            this.targetUserId = targetUserId;
        }

        public int getCoinType() {
            return coinType;
        }

        public void setCoinType(int coinType) {
            this.coinType = coinType;
        }

        public String getCoinName() {
            return coinName;
        }

        public void setCoinName(String coinName) {
            this.coinName = coinName;
        }

        public String getCoinMemo() {
            return coinMemo;
        }

        public void setCoinMemo(String coinMemo) {
            this.coinMemo = coinMemo;
        }

        public int getChangeNum() {
            return changeNum;
        }

        public void setChangeNum(int changeNum) {
            this.changeNum = changeNum;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public int getYn() {
            return yn;
        }

        public void setYn(int yn) {
            this.yn = yn;
        }
    }
}
