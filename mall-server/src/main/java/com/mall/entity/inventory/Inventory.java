package com.mall.entity.inventory;

import java.util.Date;

public class Inventory {
    private String inventoryid;

	private String storeHouseNumber;

	private String productNumber;

	private Integer amount;

	private Integer stay_amount;

	private Integer has_amount;

	private Date inStockTime;

	public String getInventoryid() {
		return inventoryid;
	}

	public void setInventoryid(String inventoryid) {
		this.inventoryid = inventoryid;
	}

	public String getStoreHouseNumber() {
		return storeHouseNumber;
	}

	public void setStoreHouseNumber(String storeHouseNumber) {
		this.storeHouseNumber = storeHouseNumber;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getStay_amount() {
		return stay_amount;
	}

	public void setStay_amount(Integer stay_amount) {
		this.stay_amount = stay_amount;
	}

	public Integer getHas_amount() {
		return has_amount;
	}

	public void setHas_amount(Integer has_amount) {
		this.has_amount = has_amount;
	}

	public Date getInStockTime() {
		return inStockTime;
	}

	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}

	
}