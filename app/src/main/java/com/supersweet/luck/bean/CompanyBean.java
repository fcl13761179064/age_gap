package com.supersweet.luck.bean;

import com.alibaba.fastjson.annotation.JSONField;


public class CompanyBean {
    private String msg;
    private Integer code;
    public Company company;


    public static class Company {
        private Integer id;
        private String chName;
        private String enName;
        private String policy;
        private String terms;

        public String getTerms() {
            return terms;
        }

        public void setTerms(String terms) {
            this.terms = terms;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getChName() {
            return chName;
        }

        public void setChName(String chName) {
            this.chName = chName;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }
    }
}
