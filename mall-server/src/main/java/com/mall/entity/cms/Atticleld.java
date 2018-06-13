package com.mall.entity.cms;

import java.util.Date;

public class Atticleld {
    private String articleId;

    private String atticletype;

    private String title;

    private String viewImg;

    private Integer viewCount;

    private Integer likeCount;

    private String status;

    private String recommended;

    private String createby;

    private Date createat;

    private Date updateTime;

    private String content;

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getAtticletype() {
        return atticletype;
    }

    public void setAtticletype(String atticletype) {
        this.atticletype = atticletype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewImg() {
        return viewImg;
    }

    public void setViewImg(String viewImg) {
        this.viewImg = viewImg;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecommended() {
        return recommended;
    }

    public void setRecommended(String recommended) {
        this.recommended = recommended;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}