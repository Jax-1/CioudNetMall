package com.mall.entity.sys;

public class CodeType {
    private String TYPE_CODE;

    private String TYPE_NAME;

    private String CREATE_AT;

    private String CREATE_BY;

    private String CREATE_BY_TXT;

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public String getTYPE_NAME() {
        return TYPE_NAME;
    }

    public void setTYPE_NAME(String TYPE_NAME) {
        this.TYPE_NAME = TYPE_NAME;
    }

    public String getCREATE_AT() {
        return CREATE_AT;
    }

    public void setCREATE_AT(String CREATE_AT) {
        this.CREATE_AT = CREATE_AT;
    }

    public String getCREATE_BY() {
        return CREATE_BY;
    }

    public void setCREATE_BY(String CREATE_BY) {
        this.CREATE_BY = CREATE_BY;
    }

    public String getCREATE_BY_TXT() {
        return CREATE_BY_TXT;
    }

    public void setCREATE_BY_TXT(String CREATE_BY_TXT) {
        this.CREATE_BY_TXT = CREATE_BY_TXT;
    }
}