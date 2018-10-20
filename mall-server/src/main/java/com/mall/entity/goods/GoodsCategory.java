package com.mall.entity.goods;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mall.util.DateFormatUtil;
import com.mall.util.SessionUtil;
import com.mall.util.UUIDUtil;

public class GoodsCategory implements Serializable{
    private String category_id;

    private String parent_id;

    private String category_name;

    private Integer priority;

    private String status;

    private String simple_describe;

    private String recommend;

    private Date create_time;

    private Date update_time;

    private String admin_id;
    
    public String toString() {
    	return category_id+"-"+parent_id+"-"+category_name;
    }
    
    
    private List<GoodsCategory> goodsCategoryList;
    
    
    
    public List<GoodsCategory> getGoodsCategoryList() {
		return goodsCategoryList;
	}

	public void setGoodsCategoryList(List<GoodsCategory> goodsCategoryList) {
		this.goodsCategoryList = goodsCategoryList;
	}

	public static GoodsCategory init(HttpServletRequest request,GoodsCategory goodsCategory) {
    	if(SessionUtil.getAdminUser(request)!=null) {
    		goodsCategory.setAdmin_id(SessionUtil.getAdminUser(request).getAdmin_name());
    	}
    	goodsCategory.setCategory_id(UUIDUtil.getUUID());
    	goodsCategory.setCreate_time(DateFormatUtil.getDate());
    	return goodsCategory;
    	
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSimple_describe() {
        return simple_describe;
    }

    public void setSimple_describe(String simple_describe) {
        this.simple_describe = simple_describe;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
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
}