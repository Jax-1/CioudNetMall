package com.mall.entity.login;

import java.util.Date;

public class Admin {
    private String admin_name;

    private String password;

    private String rand;

    private String description;

    private String state;

    private Date create_time;

    private Date update_time;

    private String del_state;

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public String getDel_state() {
        return del_state;
    }

    public void setDel_state(String del_state) {
        this.del_state = del_state;
    }
}