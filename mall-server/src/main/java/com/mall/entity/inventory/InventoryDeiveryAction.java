package com.mall.entity.inventory;

import java.util.Date;

public class InventoryDeiveryAction {
    private Integer action_id;

    private Integer delivery_id;

    private String action_user;

    private Byte action_status;

    private Byte delivery_status;

    private String action_note;

    private Date log_time;

    public Integer getAction_id() {
        return action_id;
    }

    public void setAction_id(Integer action_id) {
        this.action_id = action_id;
    }

    public Integer getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(Integer delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getAction_user() {
        return action_user;
    }

    public void setAction_user(String action_user) {
        this.action_user = action_user;
    }

    public Byte getAction_status() {
        return action_status;
    }

    public void setAction_status(Byte action_status) {
        this.action_status = action_status;
    }

    public Byte getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(Byte delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getAction_note() {
        return action_note;
    }

    public void setAction_note(String action_note) {
        this.action_note = action_note;
    }

    public Date getLog_time() {
        return log_time;
    }

    public void setLog_time(Date log_time) {
        this.log_time = log_time;
    }
}