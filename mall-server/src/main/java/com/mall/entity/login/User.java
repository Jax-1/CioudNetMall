package com.mall.entity.login;

import java.util.Date;

public class User {
    private String user_name;

    private String password;

    private String rand;

    private String status;

    private String userinfo_id;

    private Date create_at;

    private Date update_time;

    private String del_status;

    public User() {
		super();
	}

	public User(String user_name, String password, String rand, String status, String userinfo_id, Date create_at,
			Date update_time, String del_status) {
		super();
		this.user_name = user_name;
		this.password = password;
		this.rand = rand;
		this.status = status;
		this.userinfo_id = userinfo_id;
		this.create_at = create_at;
		this.update_time = update_time;
		this.del_status = del_status;
	}

	public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserinfo_id() {
        return userinfo_id;
    }

    public void setUserinfo_id(String userinfo_id) {
        this.userinfo_id = userinfo_id;
    }

    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getDel_status() {
        return del_status;
    }

    public void setDel_status(String del_status) {
        this.del_status = del_status;
    }
}