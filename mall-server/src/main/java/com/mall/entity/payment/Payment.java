package com.mall.entity.payment;

import java.util.Date;

public class Payment {
    private Integer id;

	private Integer payment_id;

	private String key;

	private String name;

	private String decriptron;

	private String state;

	private String defaultPayment;

	private String ico;

	private Date create_time;

	private Date update_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(Integer payment_id) {
		this.payment_id = payment_id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDecriptron() {
		return decriptron;
	}

	public void setDecriptron(String decriptron) {
		this.decriptron = decriptron;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDefaultPayment() {
		return defaultPayment;
	}

	public void setDefaultPayment(String defaultPayment) {
		this.defaultPayment = defaultPayment;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
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