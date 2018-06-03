package com.mall.entity.sys;

public class CodeItem {
    private String TYPE_CODE;

    private String ITEM_CODE;

    private String ITEM_NAME;

    private String ITEM_NODE;

    private String CREATE_AT;

    private String CREATE_BY;

    public String getTYPE_CODE() {
        return TYPE_CODE;
    }

    public void setTYPE_CODE(String TYPE_CODE) {
        this.TYPE_CODE = TYPE_CODE;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getITEM_NODE() {
        return ITEM_NODE;
    }

    public void setITEM_NODE(String ITEM_NODE) {
        this.ITEM_NODE = ITEM_NODE;
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
}