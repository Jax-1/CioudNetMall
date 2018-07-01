package com.mall.entity.login;

import java.util.Date;

import com.mall.entity.cms.FilePath;

public class User {
    private String user_name;

    private String password;

    private String rand;

    private String status;

    private String userinfo_id;

    private Date create_at;

    private Date update_time;

    private String del_status;
    
    /**
     * 关联用户信息表
     */
    private UserInfo userInfo;
    
    /**
     * 关联文件信息表
     */
    private FilePath filePath;
    
    
    

    public FilePath getFilePath() {
		return filePath;
	}

	public void setFilePath(FilePath filePath) {
		this.filePath = filePath;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
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