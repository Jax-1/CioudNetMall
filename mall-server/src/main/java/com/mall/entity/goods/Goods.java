package com.mall.entity.goods;

import java.util.Date;

public class Goods {
    private String goods_id;

    private String goods_info_id;

    private String goods_name;

    private String nickname;

    private String image;

    private String simple_describe;

    private String del_state;

    private String is_marketable;

    private String recommend;

    private String classic;

    private String new_product;

    private Date create_time;

    private Date update_time;

    private String admin_id;

    private String detail_describe;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_info_id() {
        return goods_info_id;
    }

    public void setGoods_info_id(String goods_info_id) {
        this.goods_info_id = goods_info_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSimple_describe() {
        return simple_describe;
    }

    public void setSimple_describe(String simple_describe) {
        this.simple_describe = simple_describe;
    }

    public String getDel_state() {
        return del_state;
    }

    public void setDel_state(String del_state) {
        this.del_state = del_state;
    }

    public String getIs_marketable() {
        return is_marketable;
    }

    public void setIs_marketable(String is_marketable) {
        this.is_marketable = is_marketable;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getClassic() {
        return classic;
    }

    public void setClassic(String classic) {
        this.classic = classic;
    }

    public String getNew_product() {
        return new_product;
    }

    public void setNew_product(String new_product) {
        this.new_product = new_product;
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

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getDetail_describe() {
        return detail_describe;
    }

    public void setDetail_describe(String detail_describe) {
        this.detail_describe = detail_describe;
    }
}