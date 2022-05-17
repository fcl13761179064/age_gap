package com.supersweet.luck.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class AllCountryBean {

    private ArrayList<Country> country;

    public ArrayList<Country> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<Country> country) {
        this.country = country;
    }

    public class Country implements Serializable {
        private  String id;
        private  String parentId;
        private  String configType;
        private  String configCode;
        private  String configName;
        private  String configValue;
        private  String configLevel;
        private  String orderBy;
        private  String yn;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getConfigType() {
            return configType;
        }

        public void setConfigType(String configType) {
            this.configType = configType;
        }

        public String getConfigCode() {
            return configCode;
        }

        public void setConfigCode(String configCode) {
            this.configCode = configCode;
        }

        public String getConfigName() {
            return configName;
        }

        public void setConfigName(String configName) {
            this.configName = configName;
        }

        public String getConfigValue() {
            return configValue;
        }

        public void setConfigValue(String configValue) {
            this.configValue = configValue;
        }

        public String getConfigLevel() {
            return configLevel;
        }

        public void setConfigLevel(String configLevel) {
            this.configLevel = configLevel;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public String getYn() {
            return yn;
        }

        public void setYn(String yn) {
            this.yn = yn;
        }
    }
}
