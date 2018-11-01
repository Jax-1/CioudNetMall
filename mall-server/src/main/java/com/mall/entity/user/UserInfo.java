package com.mall.entity.user;

import java.util.Date;

import com.mall.util.DateFormatUtil;

public class UserInfo {
    private String id;

    private String user_name;

    private String name;

    private String sex;

    private String phone;

    private String email;

    private String qq;

    private String address_province;

    private String address_city;

    private String address_area;

    private String address_street;

    private String headimgurl;

    private Date create_at;

    private Date update_time;

    private Date lastSign_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAddress_province() {
        return address_province;
    }

    public void setAddress_province(String address_province) {
        this.address_province = address_province;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_area() {
        return address_area;
    }

    public void setAddress_area(String address_area) {
        this.address_area = address_area;
    }

    public String getAddress_street() {
        return address_street;
    }

    public void setAddress_street(String address_street) {
        this.address_street = address_street;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
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

    public Date getLastSign_time() {
        return lastSign_time;
    }

    public void setLastSign_time(Date lastSign_time) {
        this.lastSign_time = lastSign_time;
    }
    /**
     * 初始化用户信息
     * @return
     */
    public static UserInfo init(User user,String id) {
    	UserInfo userInfo = new UserInfo();
    	userInfo.setUser_name(user.getUser_name());
    	userInfo.setId(id);
    	userInfo.setCreate_at(DateFormatUtil.getDate());
    	userInfo.setName("用户"+user.getUser_name());
    	userInfo.setPhone(user.getUser_name());
    	
    	return userInfo;
    }
}