package com.mall.entity.order;

import java.util.Date;

public class OrderAction {
    private Integer action_id;

	private Integer order_id;

	private String order_number;

	private String action_user;

	private Byte order_status;

	private Byte logistics_status;

	private String action_note;

	private Date log_time;

	public Integer getAction_id() {
		return action_id;
	}

	public void setAction_id(Integer action_id) {
		this.action_id = action_id;
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

	public String getAction_user() {
		return action_user;
	}

	public void setAction_user(String action_user) {
		this.action_user = action_user;
	}

	public Byte getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Byte order_status) {
		this.order_status = order_status;
	}

	public Byte getLogistics_status() {
		return logistics_status;
	}

	public void setLogistics_status(Byte logistics_status) {
		this.logistics_status = logistics_status;
	}

	public String getAction_note() {
		return action_note;
	}

	public void setAction_note(String action_note) {
		this.action_note = action_note;
	}

	public Date getLog_time() {
		return log_time;
	}

	public void setLog_time(Date log_time) {
		this.log_time = log_time;
	}

	
}