package com.mall.entity.ad;

import java.util.Date;

public class AdPosition {
    private Integer ad_position_id;

    private String name;

    private String position;

    private String measure;

    private Byte state;

    private Date create_time;

    private Date update_time;

    private Integer admin_name;

    public Integer getAd_position_id() {
        return ad_position_id;
    }

    public void setAd_position_id(Integer ad_position_id) {
        this.ad_position_id = ad_position_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Integer getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(Integer admin_name) {
        this.admin_name = admin_name;
    }
}