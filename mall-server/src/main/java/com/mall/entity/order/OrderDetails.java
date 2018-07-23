package com.mall.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import com.mall.entity.cms.FilePath;

public class OrderDetails {
    private Integer order_details_id;

    private Integer order_id;

    private String order_number;

    private String price_id;

    private String goods_id;

    private String unit_name;

    private String goods_name;

    private Integer unit_price;

    private Integer num;

    private BigDecimal details_amount;

    private Date create_time;

    private String image;
    
    /**
	 * 关联图片信息表
	 */
	private FilePath  filePath;
	

    public FilePath getFilePath() {
		return filePath;
	}

	public void setFilePath(FilePath filePath) {
		this.filePath = filePath;
	}

	public Integer getOrder_details_id() {
        return order_details_id;
    }

    public void setOrder_details_id(Integer order_details_id) {
        this.order_details_id = order_details_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getPrice_id() {
        return price_id;
    }

    public void setPrice_id(String price_id) {
        this.price_id = price_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public Integer getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(Integer unit_price) {
        this.unit_price = unit_price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getDetails_amount() {
        return details_amount;
    }

    public void setDetails_amount(BigDecimal details_amount) {
        this.details_amount = details_amount;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}