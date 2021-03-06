package com.mall.entity.goods;

import java.util.Date;

import com.mall.entity.inventory.Inventory;

public class GoodsInfo {
    private String goods_info_id;

    private String category_id;

    private String inventoryid;

    private String size;

    private String flat_feet;

    private String auth_id;

    private String framed;

    private String environment;

    private String standard;

    private String ext1;

    private String ext2;

    private String ext3;

    private Date create_time;

    private Date update_time;
    
    /**
     * 关联库存信息表
     */
    private Inventory inventory;
    private GoodsCategory goodsCategory;
    

	public GoodsCategory getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(GoodsCategory goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getGoods_info_id() {
        return goods_info_id;
    }

    public void setGoods_info_id(String goods_info_id) {
        this.goods_info_id = goods_info_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getInventoryid() {
        return inventoryid;
    }

    public void setInventoryid(String inventoryid) {
        this.inventoryid = inventoryid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getFlat_feet() {
        return flat_feet;
    }

    public void setFlat_feet(String flat_feet) {
        this.flat_feet = flat_feet;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public String getFramed() {
        return framed;
    }

    public void setFramed(String framed) {
        this.framed = framed;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3;
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
}